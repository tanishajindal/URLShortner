package resource;

import com.urlshort.Helper;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.*;
import java.util.logging.Level;

@Path("/v1/urlservice")
@Produces(MediaType.APPLICATION_JSON)
public class URLResource {
    public static int id=1;
    public static HashMap<String,Integer> map= new HashMap<>();
    public static HashMap<String,Integer> hitCounter= new HashMap<>();

    @GET
    @Path("/getOriginalURL/{shorturl}")
    public Response getOriginalURL( @PathParam("shorturl") String shorturl) {
        try {
            String originalurl="";
            if(hitCounter.containsKey(shorturl))
            {
                int val=hitCounter.get(shorturl);
                hitCounter.put(shorturl,val+1);
            }
            else
                hitCounter.put(shorturl,1);
            int shortid= Helper.shortURLtoID(shorturl);
            for (Map.Entry mapElement : map.entrySet()) {
                Integer idFromMap   = (Integer)mapElement.getValue();
                if(idFromMap==shortid)
                {
                    String mappedkey=(String)mapElement.getKey();
                    originalurl=mappedkey.substring(0, mappedkey.length() - 5);
                }
            }
            return Response.ok().entity(originalurl).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/getShortenedURL")
    public Response getShortenedURL(InputRequest req ,
            @QueryParam("clientid") String clientid) {
        //,@PathParam("Long_url") String Long_url
        try {
            int id;
            String temp=req.longurl+clientid;
            if(map.containsKey(temp))
                id=map.get(temp);
            else
            {
                id=URLResource.id++;
                map.put(temp,id);
            }
            String shorturl=Helper.idToShortURL(id);
            return Response.ok().entity(shorturl).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/getShortenedURL/{Long_url}")
    public Response getShortenedURL( @PathParam("Long_url") String Long_url,@QueryParam("clientid") String clientid) {
        try {
            int id;
            String temp=Long_url+clientid;
            if(map.containsKey(temp))
                id=map.get(temp);
            else
            {
                id=URLResource.id++;
                map.put(temp,id);
            }
            String shorturl=Helper.idToShortURL(id);
            return Response.ok().entity(shorturl).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("/getHitCount/{shorturl}")
    public Response getHitCount( @PathParam("shorturl") String shorturl) {
        try {
            Integer countRate=0;
            if(hitCounter.containsKey(shorturl))
                countRate=hitCounter.get(shorturl);
             String count=countRate.toString();
            return Response.ok().entity(countRate).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
