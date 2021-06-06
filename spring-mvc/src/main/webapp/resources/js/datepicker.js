$( function() {
    $( ".datepicker" ).datepicker({
        dateFormat: "dd.mm.yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "0000:+50",
    });
} );