package com.davs.test.springboot.springboot_test.services;

import java.math.BigDecimal;

import com.davs.test.springboot.springboot_test.models.Cuenta;

public interface CuentaService {

    Cuenta findById(Long id);

    int revisarTotalTransferencias(Long bancoId);

    BigDecimal revisarSaldo(Long cuentaId);

    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);

}
