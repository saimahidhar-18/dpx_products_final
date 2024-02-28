// import javax.ws.rs.core.Response;
// import javax.ws.rs.ext.ExceptionMapper;
// import javax.ws.rs.ext.Provider;

// import com.example.resources.Responsebody;

// @Provider
// public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

//     @Override
//     public Response toResponse(Throwable exception) {
//         Responsebody responsebody = new Responsebody();
//         responsebody.setCode("500 Internal Server Error");
//         responsebody.setMessage("An unexpected error occurred");

//         return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                 .entity(responsebody)
//                 .build();
//     }
// }
