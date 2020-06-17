package com.example.mercadoesclavoentregable.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductoContainer implements Serializable {
    @SerializedName("query")
    private String query;

    @SerializedName("results")
    private ArrayList<Producto> productoList;

    @SerializedName("paging")
    private Paging paging;


    public ProductoContainer() {
    }

    public ProductoContainer(String query, ArrayList<Producto> productoList, Paging paging) {
        this.query = query;
        this.productoList = productoList;
        this.paging = paging;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(ArrayList<Producto> productoList) {
        this.productoList = productoList;
    }

    public class Paging implements Serializable{
        @SerializedName("total")
        private String total;
        @SerializedName("offset")
        private String offset;
        @SerializedName("limit")
        private String limit;

        public Paging() {
        }

        public Paging(String total, String offset, String limit) {
            this.total = total;
            this.offset = offset;
            this.limit = limit;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }
    }

}
