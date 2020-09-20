package org.Hausarbeit.test;

import org.Hausarbeit.model.objects.dto.AutoDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AutoDTOTest {


    AutoDTO autoDTO = new AutoDTO();

    @Test
    public void testId() {
        autoDTO.setId(1);
        assertEquals(1, autoDTO.getId());
    }

    @Test
    public void testGetId_anzeige() {
        autoDTO.setId(1);
        assertEquals(1, autoDTO.getId());
    }

    @Test
    public void testBeschreibung() {
        autoDTO.setBeschreibung("Lachs");
        assertEquals("Lachs", autoDTO.getBeschreibung());
    }

    @Test
    public void testBaujahr() {
        autoDTO.setBaujahr("Lachs");
        assertEquals("Lachs", autoDTO.getBaujahr());
    }

    @Test
    public void testMarke() {
        autoDTO.setMarke("Lachs");
        assertEquals("Lachs", autoDTO.getMarke());
    }

    @Test
    public void testToString() {
        assertNotNull(autoDTO.toString());
    }

}
