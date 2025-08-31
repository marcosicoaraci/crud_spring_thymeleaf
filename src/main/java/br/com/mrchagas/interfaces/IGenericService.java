package br.com.mrchagas.interfaces;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericService<REQ, RES> {
    RES getUm(Integer id);
    List<RES> listarTodos(Pageable pageable);
    RES salvar(REQ requestDTO);
    RES atualizar(REQ requestDTO);
    void excluir(Integer id);
}