package com.example.resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.models.DataList;
import com.example.models.Product;
import com.example.services.DataListServices;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataListResource {

    DataListServices dataListServices = new DataListServices();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Product getAllDatalist(@PathParam("productid") long productId) {
        return dataListServices.getDataLists(productId);
    }

    @GET
    @Path("/{datalistid}")
    public DataList getDataList(@PathParam("productid") long productid, @PathParam("datalistid") long datalistid){
        return dataListServices.getDataList(productid,datalistid);
    }

    @POST
    public DataList addDataList(@PathParam("productid") long productid, DataList newDataList){
        return dataListServices.addDataList(productid, newDataList);
    }

    @DELETE
    @Path("/{datalistid}")
    public void deleteDataList(@PathParam("productid") long productid, @PathParam("datalistid") long datalistid){
        dataListServices.deleteDataList( productid,  datalistid);
    }

    @PUT
    @Path("/{datalistid}")
    public DataList upDataList(@PathParam("productid") long productid, @PathParam("datalistid") long datalistid, DataList newDataList){
        return dataListServices.updateDataList(productid,  datalistid, newDataList);
    }
}
