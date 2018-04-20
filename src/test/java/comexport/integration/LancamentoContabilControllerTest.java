package comexport.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import comexport.models.LancamentoContabil;
import comexport.models.Stats;
import comexport.services.LancamentoContabilService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LancamentoContabilControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LancamentoContabilService service;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MANDATORY_CONTA_MESSAGE = "Field contaContabil is mandatory";
    private static final String MANDATORY_DATA_MESSAGE = "Field data is mandatory";
    private static final String MANDATORY_VALOR_MESSAGE = "Field valor is mandatory";
    private static final String INVALID_DATA_MESSAGE = "Invalid field data, acceptable format: String(yyyyMMdd)";
    private static final String INVALID_VALOR_MESSAGE = "Invalid field valor, acceptable format: number(20,2)";
    private static final String INVALID_DATA = "Invalid data, acceptable format: contaContabil:Numeric, data:String(yyyyMMdd), valor:Numeric(20,2)";
    private static final String INVALID_DATA_GET = "Invalid data, acceptable format: contaContabil:Numeric";
    private static final LancamentoContabil lc = new LancamentoContabil(1, "20170130", new BigDecimal("100"));

    public List<LancamentoContabil> listDummy() {
        LancamentoContabil lc = new LancamentoContabil(1, "20170130", new BigDecimal("25000.17"));
        LancamentoContabil lc2 = new LancamentoContabil(1, "20170130", new BigDecimal("150.74"));
        LancamentoContabil lc3 = new LancamentoContabil(2, "20170130", new BigDecimal("20.00"));
        ArrayList list = new ArrayList<>();
        list.add(lc);
        list.add(lc2);
        list.add(lc3);
        return list;
    }

    @Test
    public void createShouldReturn200() throws Exception {
        LancamentoContabil lc = new LancamentoContabil(1, "20170130", new BigDecimal("100.1"));
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lc)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void createWithMissingFieldContaShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("valor", "100");
        js.put("data", "20170130");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(MANDATORY_CONTA_MESSAGE)));
    }

    @Test
    public void createWithMissingFieldDataShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("contaContabil", "1111");
        js.put("valor", "100");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createWithMissingFieldValorShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("contaContabil", "1111");
        js.put("data", "20170130");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(MANDATORY_VALOR_MESSAGE)));
    }

    @Test
    public void createWithInvalidContaContabilDataTypeShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("contaContabil", "xxx");
        js.put("data", "20170130");
        js.put("valor", "100");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(INVALID_DATA)));
    }

    @Test
    public void createWithInvalidDataDataFormatShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("contaContabil", "1111");
        js.put("data", "20170144");
        js.put("valor", "100");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(INVALID_DATA_MESSAGE)));
    }

    @Test
    public void createWithInvalidDataDataTypeShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("contaContabil", "1111");
        js.put("data", "xxx");
        js.put("valor", "100");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(INVALID_DATA_MESSAGE)));
    }

    @Test
    public void createWithInvalidValorDataPrecisionShouldReturn400AndMessage() throws Exception {
        JSONObject js = new JSONObject();
        js.put("contaContabil", "1111");
        js.put("data", "20170130");
        js.put("valor", "100.111");
        mvc.perform(MockMvcRequestBuilders.post("/lancamentos-contabeis/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(js.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(INVALID_VALOR_MESSAGE)));
    }

    @Test
    public void getByIdShouldReturn200() throws Exception {
        Mockito.when(service.findById(Mockito.anyString())).thenReturn(Optional.of(lc));
        mvc.perform(MockMvcRequestBuilders.get("/lancamentos-contabeis/idteste/"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(lc))));
    }

    @Test
    public void getByIdShouldReturn400WhenDontExist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lancamentos-contabeis/batata/"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(isEmptyOrNullString()));
    }

    @Test
    public void getByContaContabilShouldReturn200() throws Exception {
        ArrayList response = new ArrayList<>();
        response.add(lc);
        Mockito.when(service.findByContaContabil(Mockito.anyInt())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get("/lancamentos-contabeis/")
                .param("contaContabil", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contaContabil", is(lc.getContaContabil())))
                .andExpect(jsonPath("$[0].data", is(lc.getData())))
                .andExpect(jsonPath("$[0].valor").value(lc.getValor()));
    }

    @Test
    public void getByContaContabilShouldReturn200WhenEmptyResult() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/lancamentos-contabeis/")
                .param("contaContabil", "9999"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    public void getStatsShouldReturnStatsObject() throws Exception {
        Mockito.when(service.list()).thenReturn(listDummy());
        Stats stats = new Stats(listDummy(), Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/lancamentos-contabeis/_stats/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qtde", equalTo(3)))
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(stats))));
    }

    @Test
    public void getStatsShouldReturnStatsObjectByContaContabil() throws Exception {
        Mockito.when(service.list()).thenReturn(listDummy());
        Stats stats = new Stats(listDummy(), Optional.of(1));

        mvc.perform(MockMvcRequestBuilders.get("/lancamentos-contabeis/_stats/")
                .param("contaContabil", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qtde", equalTo(2)))
                .andExpect(content().string(equalTo(objectMapper.writeValueAsString(stats))));
    }

}
