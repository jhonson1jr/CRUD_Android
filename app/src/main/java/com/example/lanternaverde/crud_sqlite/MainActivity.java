package com.example.lanternaverde.crud_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lanternaverde.crud_sqlite.dao.PessoaDAO;
import com.example.lanternaverde.crud_sqlite.model.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaVisivel;
    Button btnNovoContato;
    Pessoa pessoa;
    PessoaDAO pessoaDAO;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciando
        listaVisivel = (ListView)findViewById(R.id.listaPessoas);
        btnNovoContato = (Button)findViewById(R.id.btnNovoContato);

        registerForContextMenu(listaVisivel); //habilitando menu de contexto(clicar e segurar pra abrir opções)
        //ao clicar em Novo Contato
        btnNovoContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirecionando pra tela de preenchimanto do cadastro:
                Intent i = new Intent(MainActivity.this, FormPessoa.class);
                startActivity(i);
            }
        });

        //ao clicar em algum dos elementos da lista:
        listaVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pegando o elemento clicado e convertendo pra um obj da classe pessoa
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);
                //chamando a tela do formulario passando esses dados por parametro:
                Intent i = new Intent(MainActivity.this, FormPessoa.class);
                i.putExtra("pessoa-enviada", pessoaEnviada); //passando os dados do registro clicado
                startActivity(i);
            }
        });

        //ao clicar e segurar em algum dos elementos da lista:
        listaVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //vai chamar o menu criado na linha 93
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                pessoa = arrayAdapterPessoa.getItem(position);//pegando o elemento
                return false;
            }
        });
    }

    //fazendo a consulta e populando a lista
    protected void popularListaPessoa(){
        pessoaDAO = new PessoaDAO(MainActivity.this); //instanciando
        arrayListPessoa = pessoaDAO.selectAllPessoas(); //atribuindo à listagem o resultado do select
        if (listaVisivel != null){
            //inserindo a listagem numa tela de exibição padrao:
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this,android.R.layout.simple_list_item_1, arrayListPessoa);
            listaVisivel.setAdapter(arrayAdapterPessoa); //atribuindo o adaptador para exibicao
        }
        pessoaDAO.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        popularListaPessoa();
    }

    //criando o menu que aparece ao clicar e segurar:
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Deletar"); //opção do menu que vai aparecer
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                pessoaDAO = new PessoaDAO(MainActivity.this);
                long retornoDB = pessoaDAO.removerRegistro(pessoa);
                if (retornoDB ==-1){
                    alerta("Erro ao excluir.");
                }else{
                    alerta("Excluído com sucesso.");
                }
                //após excluir o registro, faz o select no banco novamente:
                popularListaPessoa();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void alerta(String mensagem){
        //funcao pra exibir mensagem básica ao usuario
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
