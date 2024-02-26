package com.example.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.example.models.DataList;
import com.example.models.Product;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DataListServices {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    MongoClient mongoClient = MongoClients.create(CONNECTION_STRING);
    MongoDatabase database = mongoClient.getDatabase("data_products");
    MongoCollection<Document> collection = database.getCollection("products");

    public DataListServices(){

    }

    public Product getDataLists(long productId){
        

        Document document = collection.find(Filters.eq("id", productId)).first();
        

        long id = document.getLong("id");
        String name = document.getString("name");
        String desc = document.getString("description");
        String domain = document.getString("domain");
        String author = document.getString("producer");

        List<Document> doclist = document.getList("dataList", Document.class);
        List<DataList> datalist = new ArrayList<>();

        for (Document doc : doclist) {
            DataList dataList = new DataList();
            dataList.setUrlId(doc.getLong("urlId"));
            dataList.setUrlName(doc.getString("urlName"));
            dataList.setUrlDescription(doc.getString("urlDescription"));
            dataList.setCreationDate(doc.getString("creationDate"));
            dataList.setLastUpdateDate(doc.getString("lastUpdateDate"));
            dataList.setCopyUrl(doc.getString("copyUrl"));

            datalist.add(dataList);
        }
        Product product = new Product(id, name, desc, domain, author, datalist);
        
        return product;
    }

    public DataList getDataList(long productid, long datalistid){

        Document document = collection.find(Filters.eq("id", productid)).first();
        List<Document> doclist = document.getList("dataList", Document.class);

        if(doclist!=null){
            for (Document doc : doclist) {
                long datalistId = doc.getLong("urlId");
                if (datalistId == datalistid) {
                    DataList dataList = new DataList();
                    dataList.setUrlId(doc.getLong("urlId"));
                    dataList.setUrlName(doc.getString("urlName"));
                    dataList.setUrlDescription(doc.getString("urlDescription"));
                    dataList.setCreationDate(doc.getString("creationDate"));
                    dataList.setLastUpdateDate(doc.getString("lastUpdateDate"));
                    dataList.setCopyUrl(doc.getString("copyUrl"));
                    return dataList;
                }
                
            }
        }
        return null;

    }

    public DataList addDataList(long productid, DataList newDataList){

        Document document = collection.find(Filters.eq("id", productid)).first();

        if (document != null){
            List<Document> doclist = document.getList("dataList", Document.class);
            if (doclist == null) {
                doclist = new ArrayList<>();
            }


            newDataList.setUrlId((long)doclist.size()+211);

            Document newDocList = new Document("urlId", newDataList.getUrlId())
                .append("urlName", newDataList.getUrlName())
                .append("urlDescription", newDataList.getUrlDescription())
                .append("creationDate", newDataList.getCreationDate())
                .append("lastUpdateDate", newDataList.getLastUpdateDate())
                .append("copyUrl", newDataList.getCopyUrl());

            doclist.add(newDocList);
            collection.updateOne(Filters.eq("id", productid), Updates.set("dataList", doclist));

            return newDataList;            
            
        }
        return null;

    }

    public void deleteDataList(long productid, long datalistid){

        Document document = collection.find(Filters.eq("id", productid)).first();

        if (document != null) {
            List<Document> doclist = document.getList("dataList", Document.class);
            Iterator<Document> iterator = doclist.iterator();
    
            while (iterator.hasNext()) {
                Document doc = iterator.next();
                long currentDatalistId = doc.getLong("urlId");
    
                if (currentDatalistId == datalistid) {
                    iterator.remove();
                    collection.updateOne(Filters.eq("id", productid), Updates.set("dataList", doclist));
                    break;
                }
            }
        }
        return;
    }

    public DataList updateDataList(long productid, long  datalistid, DataList newDataList){

        Document document = collection.find(Filters.eq("id", productid)).first();

        if (document != null) {
            List<Document> doclist = document.getList("dataList", Document.class);
            Iterator<Document> iterator = doclist.iterator();
    
            while (iterator.hasNext()) {
                Document doc = iterator.next();
                long currentDatalistId = doc.getLong("urlId");
    
                if (currentDatalistId == datalistid) {
                    
                    doc.put("urlName", newDataList.getUrlName());
                    doc.put("urlDescription", newDataList.getUrlDescription());
                    doc.put("creationDate", newDataList.getCreationDate());
                    doc.put("lastUpdateDate", newDataList.getLastUpdateDate());
                    doc.put("copyUrl", newDataList.getCopyUrl());
    
                    collection.updateOne(
                        Filters.eq("id", productid),
                        Updates.set("dataList", doclist)
                    );
    
                    break;
                }
            }            
        }
        return null;
    }
}
