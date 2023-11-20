package com.autobots.automanager.servicos.atualizador;

import com.autobots.automanager.entidades.Empresa;

public class EmpresaAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();
    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
    private UsuarioAtualizador usuarioAtualizador = new UsuarioAtualizador();
    private MercadoriaAtualizador mercadoriaAtualizador = new MercadoriaAtualizador();
    private ServicoAtualizador servicoAtualizador = new ServicoAtualizador();
    private VendaAtualizador vendaAtualizador = new VendaAtualizador();

    private class EmpresaAtualizadorDados {
        void atualizar(Empresa empresa, Empresa atualizacao) {
            if (!verificador.verificar(atualizacao.getRazaoSocial())) {
                empresa.setRazaoSocial(atualizacao.getRazaoSocial());
            }
            if (!verificador.verificar(atualizacao.getNomeFantasia())) {
                empresa.setNomeFantasia(atualizacao.getNomeFantasia());
            }
            if (atualizacao.getCadastro() != null) {
                empresa.setCadastro(atualizacao.getCadastro());
            }
        }
    }

    public void atualizar(Empresa empresa, Empresa atualizacao) {
        EmpresaAtualizadorDados dados = new EmpresaAtualizadorDados();
        dados.atualizar(empresa, atualizacao);
        enderecoAtualizador.atualizar(empresa.getEndereco(), atualizacao.getEndereco());
        telefoneAtualizador.atualizar(empresa.getTelefones(), atualizacao.getTelefones());
        usuarioAtualizador.atualizar(empresa.getUsuarios(), atualizacao.getUsuarios());
        mercadoriaAtualizador.atualizar(empresa.getMercadorias(), atualizacao.getMercadorias());
        servicoAtualizador.atualizar(empresa.getServicos(), atualizacao.getServicos());
        vendaAtualizador.atualizar(empresa.getVendas(), atualizacao.getVendas());
    }
}
