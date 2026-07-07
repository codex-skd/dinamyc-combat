package com.skd.dinamyccombat.logic;

public interface ClientPlayerAttackProperties {
    int getClientComboCount();
    void setClientComboCount(int count);
    int incrementAndGetComboCount(int tickCount);
    boolean isClientAttackKeyHeld();
    void setClientAttackKeyHeld(boolean held);
}
