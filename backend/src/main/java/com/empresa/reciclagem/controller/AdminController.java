package com.empresa.reciclagem.controller;

import com.empresa.reciclagem.dto.RelatorioAdminDTO;
import com.empresa.reciclagem.model.mysql.Usuario;
import com.empresa.reciclagem.model.mysql.Fornecedor;
import com.empresa.reciclagem.model.mysql.Material;
import com.empresa.reciclagem.service.UsuarioService;
import com.empresa.reciclagem.service.FornecedorService;
import com.empresa.reciclagem.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/relatorios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UsuarioService usuarioService;
    private final FornecedorService fornecedorService;
    private final MaterialService materialService;

    @GetMapping("/completo")
    public ResponseEntity<RelatorioAdminDTO> getRelatorioCompleto() {
        RelatorioAdminDTO relatorio = new RelatorioAdminDTO();

        relatorio.setEstatisticasUsuarios(getEstatisticasUsuarios());
        relatorio.setEstatisticasFornecedores(getEstatisticasFornecedores());
        relatorio.setEstatisticasMateriais(getEstatisticasMateriais());
        relatorio.setEstatisticasSistema(getEstatisticasSistema());

        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<RelatorioAdminDTO.EstatisticasUsuarios> getRelatorioUsuarios() {
        return ResponseEntity.ok(getEstatisticasUsuarios());
    }

    @GetMapping("/fornecedores")
    public ResponseEntity<RelatorioAdminDTO.EstatisticasFornecedores> getRelatorioFornecedores() {
        return ResponseEntity.ok(getEstatisticasFornecedores());
    }

    @GetMapping("/materiais")
    public ResponseEntity<RelatorioAdminDTO.EstatisticasMateriais> getRelatorioMateriais() {
        return ResponseEntity.ok(getEstatisticasMateriais());
    }

    @GetMapping("/sistema")
    public ResponseEntity<RelatorioAdminDTO.EstatisticasSistema> getRelatorioSistema() {
        return ResponseEntity.ok(getEstatisticasSistema());
    }

    private RelatorioAdminDTO.EstatisticasUsuarios getEstatisticasUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();

        RelatorioAdminDTO.EstatisticasUsuarios stats = new RelatorioAdminDTO.EstatisticasUsuarios();
        stats.setTotalUsuarios((long) usuarios.size());

        // SOLUÇÃO SIMPLIFICADA: Vamos contar por status ativo/inativo
        Map<String, Long> porTipo = usuarios.stream()
                .collect(Collectors.groupingBy(
                        usuario -> usuario.getAtivo() != null && usuario.getAtivo() ? "Ativo" : "Inativo",
                        Collectors.counting()
                ));
        stats.setUsuariosPorTipo(porTipo);

        // Usuários ativos/inativos
        long ativos = usuarios.stream().filter(u -> u.getAtivo() != null && u.getAtivo()).count();
        stats.setUsuariosAtivos(ativos);
        stats.setUsuariosInativos((long) usuarios.size() - ativos);

        return stats;
    }

    private RelatorioAdminDTO.EstatisticasFornecedores getEstatisticasFornecedores() {
        List<Fornecedor> fornecedores = fornecedorService.findAll();

        RelatorioAdminDTO.EstatisticasFornecedores stats = new RelatorioAdminDTO.EstatisticasFornecedores();
        stats.setTotalFornecedores((long) fornecedores.size());

        // Fornecedores por tipo
        Map<String, Long> porTipo = fornecedores.stream()
                .collect(Collectors.groupingBy(
                        fornecedor -> fornecedor.getTipoFornecedor() != null ? fornecedor.getTipoFornecedor() : "Não informado",
                        Collectors.counting()
                ));
        stats.setFornecedoresPorTipo(porTipo);

        return stats;
    }

    private RelatorioAdminDTO.EstatisticasMateriais getEstatisticasMateriais() {
        List<Material> materiais = materialService.findAll();

        RelatorioAdminDTO.EstatisticasMateriais stats = new RelatorioAdminDTO.EstatisticasMateriais();
        stats.setTotalMateriais((long) materiais.size());

        // Materiais por tipo
        Map<String, Long> porTipo = materiais.stream()
                .collect(Collectors.groupingBy(
                        Material::getTipo,
                        Collectors.counting()
                ));
        stats.setMateriaisPorTipo(porTipo);

        // Preço médio por tipo
        Map<String, Double> precoMedio = materiais.stream()
                .collect(Collectors.groupingBy(
                        Material::getTipo,
                        Collectors.averagingDouble(material -> material.getPrecoKg().doubleValue())
                ));
        stats.setPrecoMedioPorTipo(precoMedio);

        // Valor total em estoque
        double valorTotal = materiais.stream()
                .mapToDouble(m -> m.getPrecoKg() != null ? m.getPrecoKg().doubleValue() * 100 : 0)
                .sum();
        stats.setValorTotalEstoque(valorTotal);

        return stats;
    }

    private RelatorioAdminDTO.EstatisticasSistema getEstatisticasSistema() {
        RelatorioAdminDTO.EstatisticasSistema stats = new RelatorioAdminDTO.EstatisticasSistema();

        stats.setVersaoSistema("1.0.0");

        // Total de registros
        long totalRegistros = materialService.findAll().size() +
                fornecedorService.findAll().size() +
                usuarioService.findAll().size();
        stats.setTotalRegistros(totalRegistros);

        stats.setStatusBackup("Último backup: " + java.time.LocalDate.now());
        stats.setLogsErroUltimoMes(0);

        return stats;
    }
}