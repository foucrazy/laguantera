/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.laguantera.action.calculadora;

import com.laguantera.dao.PeticionesBD;
import com.laguantera.util.Servicios;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fou7
 */
public class MarcaVehiculo extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String consulta=request.getParameter("consulta");            
            PeticionesBD bbdd=new PeticionesBD();
            bbdd.abrirSesion();

            List marcas=bbdd.getMarcas(consulta);
            
            JSONObject resultado=new JSONObject();
            JSONArray arrayRes=new JSONArray();
            Iterator iter = marcas.iterator();
            int contador=0;
            while(iter.hasNext()){
                JSONObject marca=new JSONObject();
                Object[] info=(Object[])iter.next();                
                marca.put("label",info[1]);
                marca.put("value", info[0]);
                arrayRes.put(contador, marca);
                contador++;
            }

            resultado.put("marcas", arrayRes);
            out.print(resultado.toString());
            
            bbdd.cerrarSesion();
        } catch (JSONException jex){
            Servicios.logear("MarcaVehiculo", jex.toString(), Servicios.DEBUG);
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate=\"collapsed\" desc=\"HttpServlet methods. Click on the + sign on the left to edit the code.\">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
