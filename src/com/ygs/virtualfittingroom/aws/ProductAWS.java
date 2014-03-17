package com.ygs.virtualfittingroom.aws;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;
import com.ygs.virtualfittingroom.db.DBfunction;
import com.ygs.virtualfittingroom.db.MySQLiteHelper;
import com.ygs.virtualfittingroom.entity.ProductCategory;
import com.ygs.virtualfittingroom.entity.ProductCategoryList;
import com.ygs.virtualfittingroom.util.Constants;

public class ProductAWS {

	private static final String PRODUCT_CATEGORY_DOMAIN = "product_category";
	private static final String PRODUCT_CATEGORY_LIST_DOMAIN = "product_category_list";
	
		
	protected AmazonSimpleDBClient sdbClient;
	protected String nextToken;
	protected int sortMethod;
	protected int count;
	private Context context;
	DBfunction db;
	public ProductAWS(Activity act) {
		// TODO Auto-generated constructor stub
	
		AWSCredentials credentials = new BasicAWSCredentials( Constants.ACCESS_KEY_ID, Constants.SECRET_KEY );
        this.sdbClient = new AmazonSimpleDBClient( credentials); 
       // sdbClient.setEndpoint("sdb.us-west-2.amazonaws.com");
        this.sdbClient.setRegion(Region.getRegion(Regions.US_WEST_2));
		this.nextToken = null;
		context = act;
		db = new DBfunction(context);
		
		this.count = -1;
	}
		
	
	public void createProductCategoryDomain() {
		CreateDomainRequest cdr = new CreateDomainRequest(PRODUCT_CATEGORY_DOMAIN);
		this.sdbClient.createDomain( cdr );
	}
	
	public void createProductCategoryListDomain() {
		CreateDomainRequest cdr = new CreateDomainRequest(PRODUCT_CATEGORY_LIST_DOMAIN);
		this.sdbClient.createDomain( cdr );
	}
        /*
     * Using the pre-defined query, extracts items from the domain in a determined order using the 'select' operation.
     */
	public synchronized List<ProductCategory> getProduct() {
		String query = "select * from "+PRODUCT_CATEGORY_DOMAIN;
		
		
		SelectRequest selectRequest = new SelectRequest( query ).withConsistentRead( true );
		selectRequest.setNextToken( this.nextToken );
		
		SelectResult response = this.sdbClient.select( selectRequest );
		this.nextToken = response.getNextToken();
		
		return this.convertItemListToSportsList( response.getItems() );	
	}
	
	public synchronized List<ProductCategoryList> getProductList(String cat_id) {
		String query = "select * from "+PRODUCT_CATEGORY_LIST_DOMAIN + " where category_id = '"+cat_id+"' ";
		
		
		SelectRequest selectRequest = new SelectRequest( query ).withConsistentRead( true );
		selectRequest.setNextToken( this.nextToken );
		
		SelectResult response = this.sdbClient.select( selectRequest );
		this.nextToken = response.getNextToken();
		
		return this.convertItemListToProductList( response.getItems() );	
	}
	
    private List<ProductCategoryList> convertItemListToProductList(
			List<Item> items) {
    	List<ProductCategoryList> scores = new ArrayList<ProductCategoryList>( items.size() );
		for ( Item item : items ) {
			scores.add( this.convertItemToProductList( item ) );
		}
		
		return scores;
	}


	private ProductCategoryList convertItemToProductList(Item item) {
		// TODO Auto-generated method stub
		String id =getStringValueForAttributeFromList( "id", item.getAttributes());
		String category = getStringValueForAttributeFromList("item", item.getAttributes());
		String img = getStringValueForAttributeFromList("image", item.getAttributes());
		String cat_id = getStringValueForAttributeFromList("category_id", item.getAttributes());
		ProductCategoryList sports = new ProductCategoryList();
		sports.setId(id);
		sports.setItem(category);
		sports.setImage(img);
		sports.setCategory_id(cat_id);
		return sports;
	}


	/*
     * Converts an array of Items into an array of HighScore objects.
     */
	protected List<ProductCategory> convertItemListToSportsList( List<Item> items ) {
		List<ProductCategory> scores = new ArrayList<ProductCategory>( items.size() );
		for ( Item item : items ) {
			scores.add( this.convertItemToHighScore( item ) );
		}
		
		return scores;
	}
	
    /*
     * Converts a single SimpleDB Item into a HighScore object.
     */
	protected ProductCategory convertItemToHighScore( Item item ) {
		String id =getStringValueForAttributeFromList( "id", item.getAttributes());
		String category = getStringValueForAttributeFromList("type", item.getAttributes());
		ProductCategory sports = new ProductCategory();
		sports.setId(id);
		sports.setType(category);
		return sports;
	}	
	    /*
     * Extracts the value for the given attribute from the list of attributes.
     * Extracted value is returned as a String.
     */
	protected String getStringValueForAttributeFromList( String attributeName, List<Attribute> attributes ) {
		for ( Attribute attribute : attributes ) {
			if ( attribute.getName().equals( attributeName ) ) {
				return attribute.getValue();
			}
		}
		
		return "";		
	}
	
    /*
     * Extracts the value for the given attribute from the list of attributes.
     * Extracted value is returned as an int.
     */
	protected int getIntValueForAttributeFromList( String attributeName, List<Attribute> attributes ) {
		for ( Attribute attribute : attributes ) {
			if ( attribute.getName().equals( attributeName ) ) {
				return Integer.parseInt( attribute.getValue() );
			}
		}
		
		return 0;		
	}	
}
