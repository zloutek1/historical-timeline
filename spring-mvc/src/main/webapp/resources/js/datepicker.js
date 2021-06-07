$( function() {
    $( ".datepicker" ).datepicker({
        dateFormat: "dd.mm.yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "100:+50",
    });
} );