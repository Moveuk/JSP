package com.saeyan.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.saeyan.dao.ProductDAO;
import com.saeyan.dto.ProductVO;

@WebServlet("/productUpdate.do")
public class ProductUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      String code = request.getParameter("code");
	      
	      ProductDAO pDao = ProductDAO.getInstance();
	      ProductVO pVo = pDao.selectProductByCode(code);
	      
	      request.setAttribute("product", pVo);
	      
	      RequestDispatcher dispatcher = request.getRequestDispatcher("product/productUpdate.jsp");
	      dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
	// 파일 업로드
		// 파일 업로드 속성 설정
		ServletContext context = getServletContext();
		String path = context.getRealPath("upload");
		System.out.println(path);
		String encType = "UTF-8";
		int sizeLimit = 20 * 1024 * 1024;
		
		// 파일 업로드 호출
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		
	// DB 연동
		// DB에 들어갈 속성 받기
		String code = multi.getParameter("code");
		String name = multi.getParameter("name");
		int price = Integer.parseInt(multi.getParameter("price"));
		String description = multi.getParameter("description");
		String pictureUrl = multi.getFilesystemName("pictureUrl");	// 서버에 저장한 파일 이름
		
		if (pictureUrl == null) {
			pictureUrl = multi.getParameter("nonmakeimg");
		}
		
		// pVo 객체 생성 및 멤버 변수 초기화
		ProductVO pVo = new ProductVO();
		pVo.setCode(Integer.parseInt(code));
		pVo.setName(name);
		pVo.setPrice(price);
		pVo.setDescription(description);
		pVo.setPictureUrl(pictureUrl);
		
		// DB Insert
		ProductDAO pDao = ProductDAO.getInstance();	// static 하므로 바로 불러옴
		pDao.updateProduct(pVo);					// DB 입력
		
		response.sendRedirect("productList.do");
	}

}
