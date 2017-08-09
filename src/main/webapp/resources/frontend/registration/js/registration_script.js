$(function(){

    $('#myForm').on('submit',function(e){
        var newUser ={};
        newUser.fname = $.trim($('#inputFirstName ').val());
        newUser.lname = $.trim($('#inputLastName ').val());
        newUser.email = $.trim($('#inputEmail2 ').val());
        if( $.trim($('#inputPassword2 ').val())!== $.trim($('#inputPassword3 ').val())){
            e.preventDefault();
            $('.confirm_pass').append('<div class="inputPassword3_error"><p>Паролі не співпадають</p></div>');
        }
            });
    });