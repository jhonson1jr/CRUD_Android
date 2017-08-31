package com.example.lanternaverde.crud_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lanternaverde.crud_sqlite.dao.PessoaDAO;
import com.example.lanternaverde.crud_sqlite.model.Pessoa;

/**
 * Created by Lanterna Verde on 27/08/2017.
 */

public class FormPessoa extends AppCompatActivity{

    //criando objetos
    EditText editNome, editEndereco, editCpf, editTelefone;
    Button btnVariavel;
    Pessoa pessoa, pessoaEditar; //pessoa caso novo registro, pessoaEditar caso editar contato
    PessoaDAO pessoaDAO;
    long retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        Intent i = getIntent(); //buscando alguma entidade que ja foi utilizada (pessoaEditar)
        pessoaEditar = (Pessoa) i.getSerializableExtra("pessoa-enviada"); //atribuindo ao objeto de edicao com uma string de identificacao
        pessoa = new Pessoa();
        pessoaDAO = new PessoaDAO(FormPessoa.this); //passando o contexto atual

        //instanciando os objetos visuais
        editNome = (EditText)findViewById(R.id.editNome);
        editEndereco = (EditText)findViewById(R.id.editEndereco);
        editCpf = (EditText)findViewById(R.id.editCPF);
        editTelefone = (EditText)findViewById(R.id.editTelefone);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        //verificando se o objeto pessoaEditar possui valores, se sim, é edição, se nao, é novo contato:
        if(pessoaEditar != null){ //é edição
            btnVariavel.setText("Salvar Edição");

            //preenchendo os dados do formulario com os registros oriundos do item clicado na listagem
            editNome.setText(pessoaEditar.getNome());
            editEndereco.setText(pessoaEditar.getEndereco());
            editCpf.setText(pessoaEditar.getCpf());
            editTelefone.setText(pessoaEditar.getTelefone());
            pessoa.setId(pessoaEditar.getId()); //passando o ID já existente

        }else{ //é novo registro
            btnVariavel.setText("Salvar Novo");
        }
        //ao clicarem no botao:
        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //atribuindo os valores digitados
                pessoa.setNome(editNome.getText().toString());
                pessoa.setEndereco(editEndereco.getText().toString());
                pessoa.setCpf(editCpf.getText().toString());
                pessoa.setTelefone(editTelefone.getText().toString());

                //verificando se é novo contato ou atualização de contato
                if(pessoaEditar != null){ //atualizar contato
                    retorno = pessoaDAO.atualizarCadastro(pessoa);
                }else{ //salvar novo contato
                    retorno = pessoaDAO.salvarCadastro(pessoa); //chamando a funcao que realiza o cadastro
                }
                //analisando o resultado do insert ou do update:
                if (retorno == -1){
                    alerta("Erro ao interagir com BD.");
                }else{
                    alerta("Sucesso.");
                }
                pessoaDAO.close(); //fechando
                finish();
            }
        });
    }

    private void alerta(String mensagem){
        //funcao pra exibir mensagem básica ao usuario
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
