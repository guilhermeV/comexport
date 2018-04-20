package comexport.unit;

import comexport.models.LancamentoContabil;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class LancamentoContabilTest {

    @Test
    public void shouldBuildCorrectly() throws ParseException {
        LancamentoContabil lc = new LancamentoContabil(1, "20170130", new BigDecimal("100.1"));
        assertNull(lc.getId());
        assertTrue(lc.getValor().equals(new BigDecimal("100.1")));

        LancamentoContabil lcBuilded = lc.build(lc);
        assertNotNull(lcBuilded.getId());
        assertTrue(lcBuilded.getValor().equals(new BigDecimal("100.10")));
    }

    @Test
    public void shouldReturnIdUUIDFormat() throws ParseException {
        LancamentoContabil lc = new LancamentoContabil(1, "20170130", new BigDecimal("100.1"));
        LancamentoContabil lcBuilded = lc.build(lc);
        String uuidPattern = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        Pattern pattern = Pattern.compile(uuidPattern);
        if (!pattern.matcher(lcBuilded.getId()).matches()) {
            fail();
        }
    }

}
