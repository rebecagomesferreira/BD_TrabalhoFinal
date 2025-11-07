package com.empresa.reciclagem.model.mongodb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "Vendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDocument {
    
    @Id
    private String id;
    
    @Field("data_venda")
    private LocalDate dataVenda;
    
    @Field("valor_total")
    private BigDecimal valorTotal;
    
    @Field("cliente")
    private ClienteInfo cliente;
    
    @Field("vendedor")
    private Vendedor vendedor;
    
    @Field("pagamento")
    private Pagamento pagamento;
    
    @Field("itens_vendidos")
    private List<ItemVenda> itensVendidos;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteInfo {
        @Field("id_cliente")
        private String idCliente;
        
        @Field("nome")
        private String nome;
        
        @Field("cpf_cnpj")
        private String cpfCnpj;
        
        @Field("contato")
        private String contato;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Vendedor {
        @Field("id_funcionario")
        private String idFuncionario;
        
        @Field("nome")
        private String nome;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pagamento {
        @Field("tipo")
        private String tipo;
        
        @Field("parcelamento")
        private Boolean parcelamento;
        
        @Field("num_parcelas")
        private Integer numParcelas;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemVenda {
        @Field("tipo_material")
        private String tipoMaterial;
        
        @Field("quantidade_kg")
        private Integer quantidadeKg;
        
        @Field("preco_unitario")
        private BigDecimal precoUnitario;
        
        @Field("subtotal")
        private BigDecimal subtotal;
    }
}
