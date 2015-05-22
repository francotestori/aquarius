/**
 * Created by franco on 22/05/2015.
 */

//HTML5 editor
$('#text-editor').wysihtml5();

//Single instance of tag inputs  -  can be initiated with simply using data-role="tagsinput" attribute in any input field
$('#source-tags').tagsinput({
    typeahead: {
        source: ['Amsterdam', 'Washington', 'Sydney', 'Beijing', 'Cairo']
    }
});