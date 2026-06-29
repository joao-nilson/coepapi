package com.example.sgpoepapi.service;

import com.example.sgpoepapi.exception.RegraNegocioException;
import com.example.sgpoepapi.exception.SenhaInvalidaException;
import com.example.sgpoepapi.model.entity.Funcionario;
import com.example.sgpoepapi.model.repository.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuncionarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private FuncionarioRepositorio repository;

    public List<Funcionario> getFuncionarios() {
        return repository.findAll();
    }

    public Optional<Funcionario> getFuncionarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        validar(funcionario);
        return repository.save(funcionario);
    }

    public UserDetails autenticar(Funcionario funcionario) {
        UserDetails user = loadUserByUsername(funcionario.getLogin());
        boolean senhasBatem = encoder.matches(funcionario.getSenha(), user.getPassword());

        if (senhasBatem) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Funcionario funcionario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));

        String[] roles = funcionario.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(funcionario.getLogin())
                .password(funcionario.getSenha())
                .roles(roles)
                .build();
    }

    @Transactional
    public void excluir(Funcionario funcionario) {
        Objects.requireNonNull(funcionario.getId());
        repository.delete(funcionario);
    }

    public void validar(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().equals("")) {
            throw new RegraNegocioException("Cargo inválido");
        }
        if (funcionario.getLogin() == null || funcionario.getLogin().trim().equals("")) {
            throw new RegraNegocioException("Login inválido");
        }
    }
}
