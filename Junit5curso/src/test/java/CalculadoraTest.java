import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculadoraTest {
	
	Calculadora calc = new Calculadora();
	static int num = 0;
	
	
	@BeforeAll
	public static void antesDeTudo() {
		System.out.println("Começo");
		
	}
	
	@BeforeEach
	public void antesDeCada() {
		num = 0;
		System.out.println("A");
		
	}

	@Test
	public void testSoma() {
		
		 Assertions.assertTrue(calc.soma(2, 3) == 5);
		 Assertions.assertEquals(5, calc.soma(2, 3));
		 System.out.println(calc.soma(2, 3));
		 num++;
		 System.out.println(num);
	}
	
	
	@Test
	public void testSoma2() {
		
		 Assertions.assertTrue(calc.soma(2, 3) == 5);
		 Assertions.assertEquals(5, calc.soma(2, 3));
		 System.out.println(calc.soma(2, 3));
		 num++;
		 System.out.println(num);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Teste1", "Teste2"})
	public void testStrings(String param) {
		System.out.println(param);
		assertNotNull(param);
	}
	
}
