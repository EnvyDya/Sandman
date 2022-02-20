package com.sandman.game.sprites.interfaces;

public interface Danger {

    /**
     * Méthode qui vérifie si le danger peut tué
     * ex : renvoie false si le danger est gelé et true s'il n'est pas gelé
     */
    public Boolean canKill();
}
