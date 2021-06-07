package cz.fi.muni.pa165.rest.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.fi.muni.pa165.RootWebContext;
import cz.muni.fi.pa165.dto.TimelineCreateDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.dto.TimelineUpdateDTO;
import cz.muni.fi.pa165.exceptions.ServiceException;
import cz.muni.fi.pa165.facade.TimelineFacade;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class TimelineControllerTest extends AbstractTestNGSpringContextTests {
    @Mock
    private TimelineFacade timelineFacade;

    @InjectMocks
    private TimelineController timelineController;

    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @BeforeMethod
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(timelineController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @AfterMethod
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void create_valid_creates() throws Exception {
        TimelineCreateDTO timeline = new TimelineCreateDTO();
        timeline.setName("T1");
        timeline.setFromDate(LocalDate.of(2000,5,28));
        timeline.setToDate(LocalDate.of(2000,6,29));

        doReturn(1L)
                .when(timelineFacade).createTimeline(any(TimelineCreateDTO.class));
        doReturn(Optional.of(twoTimelines().get(0)))
                .when(timelineFacade).findById(1L);

        String json = convertToJson(timeline);

        mockMvc.perform(post("/timelines/create").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void create_invalid_throws() throws Exception {
        doThrow(new ServiceException("Resource already exists"))
                .when(timelineFacade).createTimeline(any(TimelineCreateDTO.class));

        mockMvc.perform(post("/timelines/create")).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void update_valid_updates() throws Exception {
        TimelineUpdateDTO uTimeline = new TimelineUpdateDTO();
        uTimeline.setName("T1");
        uTimeline.setFromDate(LocalDate.of(2000,5,28));
        uTimeline.setToDate(LocalDate.of(2000,6,29));
        uTimeline.setId(1L);

        doNothing()
                .when(timelineFacade).updateTimeline(any(TimelineUpdateDTO.class));
        doReturn(Optional.of(twoTimelines().get(0)))
                .when(timelineFacade).findById(1L);

        String json = convertToJson(uTimeline);

        mockMvc.perform(put("/timelines/update").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void update_invalid_throws() throws Exception {
        doThrow(new ServiceException("Resource not modified"))
                .when(timelineFacade).updateTimeline(any(TimelineUpdateDTO.class));

        mockMvc.perform(put("/timelines/update")).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void delete_valid_deletes() throws Exception {
        doNothing()
                .when(timelineFacade).deleteTimeline(anyLong());

        mockMvc.perform(delete("/timelines/1")).andExpect(status().isOk());
    }

    @Test
    public void delete_invalid_throws() throws Exception {
        doThrow(new ServiceException("Resource not found"))
                .when(timelineFacade).deleteTimeline(anyLong());

        mockMvc.perform(delete("/timelines/1")).andExpect(status().isNotFound());
    }

    @Test
    public void findAll_findsAll() throws Exception {
        doReturn(twoTimelines())
                .when(timelineFacade).findAll();

        mockMvc.perform(get("/timelines"))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.[?(@.id==1)].name").value("T1"));

        mockMvc.perform(get("/timelines"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("T2"));
    }

    @Test
    public void findBetweenDates_dates_findsInScope() throws Exception {
        LocalDate since = LocalDate.of(1999, 12,25);
        LocalDate to = LocalDate.of(2000, 12,25);

        doReturn(Collections.unmodifiableList(List.of(twoTimelines().get(0))))
                .when(timelineFacade).findAllBetweenDates(since, to);

        var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        mockMvc.perform(get(String
                .format("/timelines/between/%s/%s", since.format(dateFormat), to.format(dateFormat))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("T1"));
    }

    @Test
    public void findById_validId_findsTimeline() throws Exception {
        doReturn(Optional.of(twoTimelines().get(1)))
                .when(timelineFacade).findById(2L);

        mockMvc.perform(get("/timelines/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("T2"));
    }

    @Test
    public void findById_invalidId_findsTimeline() throws Exception {
        doReturn(Optional.empty())
                .when(timelineFacade).findById(1L);

        mockMvc.perform(get("/timelines/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void findByName_validName_findsTimeline() throws Exception {
        doReturn(Optional.of(twoTimelines().get(0)))
                .when(timelineFacade).findByName("T1");

        mockMvc.perform(get("/timelines/named/T1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1L));
    }

    private static String convertToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(obj);
    }

    private List<TimelineDTO> twoTimelines(){
        TimelineDTO timeline1 = new TimelineDTO();
        timeline1.setName("T1");
        timeline1.setFromDate(LocalDate.of(2000,5,28));
        timeline1.setToDate(LocalDate.of(2000,6,29));
        timeline1.setId(1L);

        TimelineDTO timeline2 = new TimelineDTO();
        timeline2.setName("T2");
        timeline2.setFromDate(LocalDate.of(2001,5,28));
        timeline2.setToDate(LocalDate.of(2001,6,29));
        timeline2.setId(2L);

        return List.of(timeline1, timeline2);
    }


























}
