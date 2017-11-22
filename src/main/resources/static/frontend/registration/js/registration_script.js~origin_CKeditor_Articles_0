$(function(){

    $('#myForm').on('submit',function(e){
        var newUser ={};
        newUser.fname = $.trim($('#inputFirstName ').val());
        newUser.lname = $.trim($('#inputLastName ').val());
        newUser.email = $.trim($('#inputEmail2 ').val());
        if( $.trim($('#inputPassword2 ').val())!== $.trim($('#inputPassword3 ').val())){
            e.preventDefault();
            $('.confirm_pass').append('<div class="inputPassword3_error"><p>Паролі не співпадають</p></div>');
        }else {
            $('.inputPassword3_error').remove();
            newUser.login = $.trim($('#inputPassword2 ').val());

            var formData = JSON.stringify(newUser);
            $.ajax({
                type: "POST",
                url: server + "user/registration",
                data: formData,
                crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },
                dataType: "json",
                contentType: "application/json"
            })
                .done(function (data) {
                    console.log('успех');
                })
                .fail(function (err) {
                    console.log(err.message);
                });
        }
        console.log(newUser);
    });

    $('#enterForm').on('submit',function(e){
        e.p
        $.ajax({
            url: server + "user/",
            crossDomain: true,
            xhrFields: {withCredentials: true},

            dataType: 'json'
        })
            .done(function(data){
                insert_data_in_private_office_fields( JSON.stringify(data));
            });
    });

















    // $('.log_in_field_main_logIn').on('click',function(){
    //     var user = {
    //         fname: "This is vova123",
    //         lname: "fdfdf444444d",
    //         mname: "qwerty44",
    //         email: "myemail@gmail.com",
    //         avatarURL: "https://pickaface.net/gallery/avatar/20131210_213603_2798_lachu.png",
    //         rang: {
    //             "rangId": 2,
    //             "rangName": "LEVEL_2"
    //         }
    //     };
    //     var formData = JSON.stringify(user);
    //     console.log(formData);
    //     $.ajax({
    //         type: "POST",
    //         url: server + "user/",
    //         data: formData,
    //         crossDomain: true,
    //         xhrFields: {
    //             withCredentials: true
    //         },
    //         dataType: "json",
    //         contentType : "application/json"
    //     })
    //         .done(function(data){
    //             console.log(data);
    //         })
    //         .fail(function(err){
    //             console.log(err);
    //         });
    // });

});