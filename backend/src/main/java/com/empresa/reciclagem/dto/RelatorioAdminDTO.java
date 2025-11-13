package com.empresa.reciclagem.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class RelatorioAdminDTO {
    private EstatisticasUsuarios estatisticasUsuarios;
    private EstatisticasFornecedores estatisticasFornecedores;
    private EstatisticasMateriais estatisticasMateriais;
    private EstatisticasSistema estatisticasSistema;

    @Data
    public static class EstatisticasUsuarios {
        private Long totalUsuarios;
        private Map<String, Long> usuariosPorTipo;
        private Long usuariosAtivos;
        private Long usuariosInativos;
    }

    @Data
    public static class EstatisticasFornecedores {
        private Long totalFornecedores;
        private Map<String, Long> fornecedoresPorTipo;
    }

    @Data
    public static class EstatisticasMateriais {
        private Long totalMateriais;
        private Map<String, Long> materiaisPorTipo;
        private Map<String, Double> precoMedioPorTipo;
        private Double valorTotalEstoque;
    }

    @Data
    public static class EstatisticasSistema {
        private String versaoSistema;
        private Long totalRegistros;
        private String statusBackup;
        private Integer logsErroUltimoMes;
    }
}