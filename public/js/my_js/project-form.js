/**
 * Created by franco on 22/05/2015.
 */

$(document).ready(function(){

    //Date Pickers
    $('.input-append.date').datepicker({
        autoclose: true,
        todayHighlight: true
    });

    $('#dp5').datepicker();

    $('#sandbox-advance').datepicker({
        format: "dd/mm/yyyy",
        startView: 1,
        daysOfWeekDisabled: "3,4",
        autoclose: true,
        todayHighlight: true
    });

    //Input mask - Input helper
    $(function($){
        $("#date").mask("99/99/9999");
        $("#phone").mask("(999) 999-9999");
        $("#tin").mask("99-9999999");
        $("#ssn").mask("999-99-9999");
    });

    //Autonumeric plug-in - automatic addition of dollar signs,etc controlled by tag attributes
    $('.auto').autoNumeric('init');

    //HTML5 editor
    $('#text-editor').wysihtml5();

    //Single instance of tag inputs  -  can be initiated with simply using data-role="tagsinput" attribute in any input field
    $('#source-tags').tagsinput({
        typeahead: {
            source: ['Amsterdam', 'Washington', 'Sydney', 'Beijing', 'Cairo']
        }
    });

    //Drag n Drop up-loader
    $("div#myId").dropzone({ url: "/file/post" });
});