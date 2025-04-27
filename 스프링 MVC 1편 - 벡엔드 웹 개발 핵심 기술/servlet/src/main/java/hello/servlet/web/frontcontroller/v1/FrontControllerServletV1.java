package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
* /front-controller/v1/*로 들어오는 모든 요청을 받아서
어떤 컨트롤러(ControllerV1)를 실행할지 결정해주는 중앙 제어 역할
누구를 부를지 결정하는 요청의 입구
 */
@WebServlet (name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    //요청 URI(String) → 컨트롤러 구현체 객체(ControllerV1) 로 연결해주는 역할
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1(){
        //각 요청 URL에 해당하는 컨트롤러 객체를 controllerMap에 등록
        //예 : /front-controller/v1/members/new-form이 들어오면 MemberFormControllerV1 구현체가 연결
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        //요청 URI 추출
        String requestURI = request.getRequestURI();
        //매핑된 컨트롤러 찾기
        ControllerV1 controller = controllerMap.get(requestURI);

        //없으면 404 응답
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //있으면 해당 컨트롤러 실행
        controller.process(request,response);
    }
}
