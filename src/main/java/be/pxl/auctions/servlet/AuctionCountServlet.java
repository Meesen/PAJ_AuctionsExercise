package be.pxl.auctions.servlet;

import be.pxl.auctions.dao.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "/count")
public class AuctionCountServlet extends HttpServlet {

    private final AuctionRepository auctionRepository;

    @Autowired
    public AuctionCountServlet(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        PrintWriter writer = resp.getWriter();
        writer.println("Er zijn momenteel " + auctionRepository.findAll().stream().count() + " veiling actief.");

    }

}
