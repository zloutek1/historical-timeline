package cz.fi.muni.pa165.rest.controllers;


import cz.fi.muni.pa165.RootWebContext;
import cz.muni.fi.pa165.dto.EventDTO;
import cz.muni.fi.pa165.facade.EventFacade;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class EventControllerTest {
    @Mock
    private EventFacade eventFacade;

    @InjectMocks
    private EventController eventController;

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @BeforeMethod
    public void setup(){
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(eventController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @AfterMethod
    public void close() throws Exception {
        closeable.close();
    }

    @Test
    public void findAll_findsAll() throws Exception {
        doReturn(twoEvents())
                .when(eventFacade).findAllEvents();

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("E1"));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("E2"));
    }

    @Test
    public void findBetweenDates_dates_findsInScope() throws Exception {
        LocalDate since = LocalDate.of(1999, 12,25);
        LocalDate to = LocalDate.of(2000, 12,25);

        doReturn(List.of(twoEvents().get(0)))
                .when(eventFacade).findAllInRange(since, to);

        var dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        mockMvc.perform(get(String
                .format("/events?fromDate=%s&toDate=%s", since.format(dateFormat), to.format(dateFormat))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("E1"));
    }

    private List<EventDTO> twoEvents(){
        EventDTO event1 = new EventDTO();
        event1.setName("E1");
        event1.setDate(LocalDate.of(2000,5,28));
        event1.setDescription("Description of event 1");
        event1.setId(1L);

        EventDTO event2 = new EventDTO();
        event2.setName("E2");
        event2.setDate(LocalDate.of(2001,5,28));
        event2.setDescription("Description of event 2");
        event2.setId(2L);

        return List.of(event1, event2);
    }
}
