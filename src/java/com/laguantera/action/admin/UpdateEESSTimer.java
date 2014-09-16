package com.laguantera.action.admin;
import com.laguantera.util.Servicios;
import java.util.TimerTask;
/**
 *
 * @author Desarrollo
 */
public class UpdateEESSTimer extends TimerTask
{

    public UpdateEESSTimer()
    {
    }

    public void run ()
    {
        Servicios.logear("UpdateEESSTimer", "Realizando actualizacion de las EESS.", Servicios.INFO);
        UpdateEESS upd=new UpdateEESS();
        upd.procesar();
    }

}
