$(document).ready(function() {
        $('#contaTabela tbody tr').click(function() {
            $('#contaTabela tbody tr').removeClass('selected');
            $(this).addClass('selected');
        });
    });
    
