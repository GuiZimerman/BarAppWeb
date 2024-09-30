$(document).ready(function() {
        // Quando clicar em uma linha da tabela
        $('#contaTabela tbody tr').click(function() {
            // Remove a classe 'selected' de todas as linhas
            $('#contaTabela tbody tr').removeClass('selected');
            
            // Adiciona a classe 'selected' apenas à linha clicada
            $(this).addClass('selected');
        });
    });
    
$(document.ready(function() {
    alert(Olá!)
});