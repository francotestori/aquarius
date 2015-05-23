var $inputProfPic = $('#profile-pic');
var $previewProfPic = $('#preview');

// Differed clicking
function clickUploadFile(){
    $inputProfPic.click();
}

// Update upload image
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $previewProfPic.attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$inputProfPic.change(function () {
    readURL(this);
    $previewProfPic.guillotine('remove');
});

// Guillotine croping
$previewProfPic.on('load', function () {
    $previewProfPic.guillotine({width: 300, height: 300});
    $('#rotate_left').click(function () {
        $previewProfPic.guillotine('rotateLeft');
    });
    $('#rotate_right').click(function () {
        $previewProfPic.guillotine('rotateRight');
    });
    $('#fit').click(function () {
        $previewProfPic.guillotine('fit');
    });
    $('#zoom_in').click(function () {
        $previewProfPic.guillotine('zoomIn');
    });
    $('#zoom_out').click(function () {
        $previewProfPic.guillotine('zoomOut');
    })
});


