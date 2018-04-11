/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import beans.KalenderTag;
import beans.Termin;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dynamitos
 */
public class TerminServlet extends HttpServlet {

    public static final String EM = "EM";
    public static final String KALENDER = "KALENDER";
    public static final String CURRENTTAG = "currentTag";

    @Override
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("puKalender");
        EntityManager em = emf.createEntityManager();
        getServletContext().setAttribute(EM, em);
        TypedQuery<KalenderTag> kalenderQuery = em.createNamedQuery("loadAll", KalenderTag.class);
        List<KalenderTag> kalenderRes = kalenderQuery.getResultList();
        Map<LocalDate, KalenderTag> kalenderMap = new HashMap<>();
        for (KalenderTag kalenderRe : kalenderRes) {
            kalenderMap.put(kalenderRe.getDatum(), kalenderRe);
        }
        getServletContext().setAttribute(KALENDER, kalenderMap);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<LocalDate, KalenderTag> map = (Map<LocalDate, KalenderTag>) getServletContext().getAttribute(KALENDER);
        KalenderTag currentTag = (KalenderTag) request.getSession().getAttribute(CURRENTTAG);
        if (currentTag == null) {
            currentTag = new KalenderTag();
            currentTag.setDatum(LocalDate.now());
            map.put(currentTag.getDatum(), currentTag);
        }
        LocalDate newDate = currentTag.getDatum();
        if (request.getParameter("right") != null) {
            newDate = currentTag.getDatum().plusDays(1);
        } else if (request.getParameter("left") != null) {
            newDate = currentTag.getDatum().minusDays(1);
        }
        KalenderTag newTag = map.get(newDate);
        if(newTag == null)
        {
            newTag = new KalenderTag();
            newTag.setDatum(newDate);
            map.put(newDate, newTag);
        }
        request.getSession().setAttribute(CURRENTTAG, newTag);
        
        if(request.getParameter("speichern") != null)
        {
            String text = request.getParameter("text");
            byte von = Byte.parseByte(request.getParameter("von"));
            byte bis = Byte.parseByte(request.getParameter("bis"));
            if(text.isEmpty())
            {
                throw new IllegalArgumentException("Termin muss mindestens ein Zeichen lang sein");
            }
            if(von < 6 || von > 24 || bis < 6 || bis > 24)
            {
                throw new IllegalArgumentException("Bitte nur  ganze Zahlen von 6-24 verwenden!");
            }
            Termin t = new Termin();
            t.setBis(bis);
            t.setVon(von);
            t.setText(text);
            newTag.addTermin(t);
        }
        
        request.getRequestDispatcher("/jsp/Kalender.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
