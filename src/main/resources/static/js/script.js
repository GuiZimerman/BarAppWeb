  //Variáveis
    var selectedId = null; 
    var selectedRow = null;

// Função para a tabela com id 'contasAbertasTbl'
$(document).ready(function () {
    
    $('#contasAbertasTbl tbody tr').click(function () {
        if (selectedRow) {
            $(selectedRow).removeClass('selected');
        }

        $(this).addClass('selected'); 
        selectedRow = this; 

        selectedId = $(this).children('td:first').text(); 
    });
});

// Função para redirecionar para a página de detalhes da conta
function detalharConta() {
    if (selectedId) {
        window.location.href = `/contaDetalhada/${selectedId}`;
    } else {
        alert("Por favor, selecione uma conta primeiro.");
    }
}

function fazerPedido() {
    if (selectedId) {
        window.location.href = `/fazerPedido/${selectedId}`;
    } else {
        alert("Por favor, selecione uma conta primeiro.");
    }
}


//Função para selecionar item na AbrirContaTbl
$(document).ready(function () {
    var selectedRow = null;  
    var selectedId = null;   

    $('#abrirContaTbl tbody tr').click(function () {
        if (selectedRow) {
            $(selectedRow).removeClass('selected'); 
        }

        $(this).addClass('selected');  
        selectedRow = this;           

        selectedId = $(this).children('td:first').text(); 
        $('#clienteIdInput').val(selectedId); 
    });

    $('#abrirContaForm').on("submit", function(event) {
        var clienteId = $('#clienteIdInput').val(); 

        if (!clienteId) {
            event.preventDefault(); 
            alert("Um cliente deve ser selecionado."); 
        }
    }); 
});


// Função para a tabela com id 'estoqueTabela'
$(document).ready(function () {
    $('#estoqueTabela tbody tr').click(function () {
        if (selectedRow) {
            $(selectedRow).removeClass('selected');
        }

        $(this).addClass('selected');
        selectedRow = this;

        selectedId = $(this).children('td:first').text();
    });
});

function atualizarProduto() {
    if (selectedId) {
        window.location.href = `/estoque/atualizar?id=${selectedId}`; 
    } else {
        alert("Por favor, selecione um produto primeiro.");
    }
}

function deletarProduto() {
    if (selectedId) {
        if (confirm("Tem certeza que deseja excluir este produto?")) {
            window.location.href = `/estoque/deletar?id=${selectedId}`;
        }
    } else {
        alert("Por favor, selecione um produto primeiro.");
    }
}


function exibirDetalhesProduto() {
    var selectProduto = document.getElementById("produto");
    var selectedOption = selectProduto.options[selectProduto.selectedIndex];

    var valorProduto = selectedOption.getAttribute("data-valor");
    var quantidadeProduto = selectedOption.getAttribute("data-quantidade");

    document.getElementById("valorProduto").innerText = valorProduto ? valorProduto : "";
    document.getElementById("quantidadeProduto").innerText = quantidadeProduto ? quantidadeProduto : "";
}

//Validações CadastroCliente
 $(document).ready(function() {
                $('#clienteForm').on('submit', function(event) {
                    event.preventDefault();
                    
                    if (emptyFields() || !validaNome() || !validaTelefone()) {
                        return; // 
                    }
                    
                    this.submit();
                });
                
                function emptyFields() {
                    let nome = $('#nome').val().trim();
                    let telefone = $('#telefone').val().trim();

                    if (nome === '') {
                        alert("Nome do cliente não pode estar vazio.");
                        return true;
                    } else if (telefone === '') {
                        alert("Telefone não pode estar vazio.");
                        return true;
                    }
                    return false;
                }

                function validaNome() {
                    let nome = $('#nome').val().trim();
                    if (!/^[\p{L}'\s]+$/u.test(nome)) {
                        alert("Nome do cliente inválido.");
                        return false;
                    }
                    return true;
                }

                function validaTelefone() {
                    let telefone = $('#telefone').val().replace(/\D/g, ''); 
                    if (!/^\d{10,11}$/.test(telefone)) {
                        alert("Número de telefone inválido.\nInsira o telefone. Ex: (DD)981175456");
                        return false;
                    }
                    return true;
                }
            });

