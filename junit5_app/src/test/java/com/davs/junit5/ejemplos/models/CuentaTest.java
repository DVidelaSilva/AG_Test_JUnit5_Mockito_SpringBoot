package com.davs.junit5.ejemplos.models;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.davs.junit5.ejemplos.exceptions.DineroInsuficienteException;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest(){
        this.cuenta = new Cuenta("Diego", new BigDecimal("1000.123456"));
        System.out.println("Iniciando el Metodo");
    }

    @AfterEach
    void tearDown(){
        System.out.println("Finalizando el metodo de Prueba");
    }


    @BeforeAll
    static void BeforeAll() {
        System.out.println("Inicializando el test");
    }

    @AfterAll
    static void AfterAll() {
        System.out.println("Finalizando el test");
    }
    

    @Nested
    @Tag("cuenta")
    @DisplayName("Probando atributos de la cuenta corriente")
    class CuentaTestNombreSaldo {

        @Test
        @DisplayName("Probando nombre de la cuenta corriente!")
        void testNombreCuenta(TestInfo testInfo, TestReporter testReporter) {
            System.out.println(" ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod()
            + " copn las etiquetas " + testInfo.getTags());
            String esperado = "Diego";
            String real = cuenta.getPersona();
            assertNotNull(real, () -> "La cuenta no puede ser Nula");
            assertEquals(esperado, real, () -> "El nombre de la cuenta no es el que se esperaba");
            assertTrue(real.equals("Diego"), () -> "Nombre cuenta esperada debe ser igual a la real");
        }

        @Test
        void testSaldoCuenta(){
            assertNotNull(cuenta.getSaldo());
            assertEquals(1000.123456, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @Test
        @DisplayName("Testeando referencias que sean iguales con el metodo equals")
        void testReferenciaCuenta() {         
            Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
            Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
            assertEquals(cuenta, cuenta2);           
        }
    }

    @Nested
    class CuentaOperacionesTest{

        @Test
        void testDebidoCuenta() {
        cuenta = new Cuenta("Diego", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.12345", cuenta.getSaldo().toPlainString());
        }


        @Test
        void testCreditoCuenta() {
        cuenta = new Cuenta("Diego", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Diego", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());     
        }
    }
    

    

    @Test
    void testDineroInsuficienteExceptionCuenta() {
            Cuenta cuenta = new Cuenta("Diego", new BigDecimal("1000.12345"));

            Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
                cuenta.debito(new BigDecimal(1500));
            });

            String actual = exception.getMessage();
            String esperado = "Dinero Insuficiente";

            assertEquals(esperado, actual);
    }


    @Test
    //@Disabled
    void testRelacionBancoCuentas() {
        //fail();
        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Diego", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);


        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(() -> {assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "El valor de saldo de la cuenta2 no es el esperado");},
                  () -> {assertEquals("3000", cuenta1.getSaldo().toPlainString(), () -> "El valor de saldo de la cuenta2 no es el esperado");},
                  () -> {assertEquals(2, banco.getCuentas().size(), () -> "El banco no tiene las cuentas esperadas");},
                  () -> {assertEquals("Banco del Estado", cuenta1.getBanco().getNombre());},
                  () -> {assertEquals("Diego", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Diego")).findFirst().get().getPersona());},
                  () -> {assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Diego")));});

    }

    @Nested
    class SistemaOperativoTest{

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {
        }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac() {
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows(){
        }

        @Test
        @DisabledOnOs({OS.LINUX, OS.MAC})
        void testNoLinuxMac(){
        }
    }

    @Nested
    class JavaVersionTest {

        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void soloJdk8() {
        }

        @Test
        @DisabledOnJre(JRE.JAVA_15)
        void soloJdk15() { 
        }
    }
    
    @Nested
    class SistemPropertiesTest {

        @Test
        void imprimirSystemProperties() {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));
        }
     
        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = "17.0.12")
        void testJavaVersion(){
        }
    
        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testSolo64(){
        }
    
        @Test
        @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testNo64(){
        }
    
        @Test
        @EnabledIfSystemProperty(named = "user.name", matches = "Diego Videla Silva")
        void name(){
        }
        
        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = "dev")
        void testDev(){
        }
    }

    @Nested
    class VariableAmbientesTest {
        @Test
        void imprimirVariablesAmbiente(){
            Map<String, String> getenv = System.getenv();
            getenv.forEach((k, v) -> System.out.println(k + " = " + v));
        }
        
        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-15.0.1.*")
        void testJavaHome(){
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "8")
        void testProcesadores(){
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "ENV", matches = "dev")
        void testEnv(){
        }
    }
    



    @Test
    void testSaldoCuentaDev(){
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev);
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.123456, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testSaldoCuentaDev2(){
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(esDev, ()-> {
            
            assertNotNull(cuenta.getSaldo());
            assertEquals(1000.123456, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);

        });
    }

    @DisplayName("Probando Debito Cuenta Repetir")
    @RepeatedTest(value = 5, name="Repeticion numero: {currentRepetition} de {totalRepetitions}")
    void testDebidoCuentaRepetir() {
    cuenta = new Cuenta("Diego", new BigDecimal("1000.12345"));
    cuenta.debito(new BigDecimal(100));
    assertNotNull(cuenta.getSaldo());
    assertEquals(900, cuenta.getSaldo().intValue());
    assertEquals("900.12345", cuenta.getSaldo().toPlainString());
    }




    @Tag("param")
    @Nested
    class PruebasParametrizadasTest{

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @ValueSource(strings = {"100", "200", "300", "400", "500", "1000.12345"})
        void testDebidoCuentaValueSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"1,100", "2,200", "3,300", "4,400", "5,500", "6,1000.12345"})
        void testDebidoCuentaCsvSource(String index, String monto) {
            System.out.println(index + " -> " + monto);
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvSource({"200,100,John, John", "250,200, Pepe, Pepe", "301,300,Maria,Maria", "410,400,Pepa,Pepa", "750,500,Lucas,Lucas", "1001.12345,1000.12345,cata,cata"})
        void testDebidoCuentaCsvSource2(String saldo, String monto, String esperado, String actual) {
            System.out.println(saldo + " -> " + monto);
            cuenta.setSaldo(new BigDecimal(saldo));
            cuenta.debito(new BigDecimal(monto));
            cuenta.setPersona((actual));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
            assertEquals(esperado, actual);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
        @CsvFileSource(resources = "/data.csv")
        void testDebidoCuentaCsvFileSource(String monto) {
            System.out.println(monto);
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

    }





    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @MethodSource("montoList")
    void testDebidoCuentaMethodSource(String monto) {
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo());
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    static  List<String> montoList(){
        return Arrays.asList("100", "200", "300", "400", "500", "1000.12345");
    }


    @Nested
    class EjemploTimeOutTest {

        @Test
        @Timeout(2)
        void pruebaTimeOut() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(1);
        }
    
        @Test
        @Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
        void pruebaTimeOut2() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        @Test
        void testTimeOutAssertion(){
            assertTimeout(Duration.ofSeconds(6),() -> {
                TimeUnit.MILLISECONDS.sleep(5000);
            } );
        }
    }




    



}
