package comexport.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import comexport.annotations.ValidDate;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class LancamentoContabil {

    @Id
    @JsonIgnore
    public String id;

    @NotNull(message = "Field contaContabil is mandatory")
    public Integer contaContabil;

    @NotNull(message = "Field data is mandatory")
    @ValidDate(message = "Invalid field data, acceptable format: String(yyyyMMdd)")
    public String data;

    @NotNull(message = "Field valor is mandatory")
    @Digits(integer = 20, fraction = 2, message = "Invalid field valor, acceptable format: number(20,2)")
    public BigDecimal valor;

    public LancamentoContabil() {
    }

    public LancamentoContabil(Integer contaContabil, String data, BigDecimal valor) {
        this.contaContabil = contaContabil;
        this.data = data;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("%s", id);
    }

    public LancamentoContabil build(LancamentoContabil lancamentoContabil) {
        this.id = UUID.randomUUID().toString();
        this.valor = valor.setScale(2, RoundingMode.UNNECESSARY);
        return lancamentoContabil;
    }

    public String getId() {
        return this.id;
    }

    public void setContaContabil(Integer contaContabil) {
        this.contaContabil = contaContabil;
    }

    public Integer getContaContabil() {
        return this.contaContabil;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

//    public void setData(String data) {
//        this.data = data;
//    }

    public String getData() {
        return this.data;
    }

}
