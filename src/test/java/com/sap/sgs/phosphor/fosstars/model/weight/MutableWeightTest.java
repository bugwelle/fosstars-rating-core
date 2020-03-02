package com.sap.sgs.phosphor.fosstars.model.weight;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Test;

public class MutableWeightTest {

  @Test
  public void good() {
    assertEquals(0.45, new MutableWeight(0.45).value(), 0.0);
    assertEquals(1, new MutableWeight(1).value(), 0.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void zero() {
    new MutableWeight(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negative() {
    new MutableWeight(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooBig() {
    new MutableWeight(2);
  }

  @Test
  public void update() {
    MutableWeight weight = new MutableWeight(0.5);
    assertEquals(0.5, weight.value(), 0.001);
    weight.value(0.9);
    assertEquals(0.9, weight.value(), 0.001);
  }

  @Test
  public void serializeAndDeserialize() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    MutableWeight weight = new MutableWeight(0.5);
    byte[] bytes = mapper.writeValueAsBytes(weight);
    MutableWeight clone = mapper.readValue(bytes, MutableWeight.class);
    assertEquals(weight, clone);
  }
}