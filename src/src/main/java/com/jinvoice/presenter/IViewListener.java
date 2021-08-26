package com.jinvoice.presenter;

public interface IViewListener
{
	public void onAddItemButtonClicked();
	public void onRemoveSelectedItemButtonClicked();
	
	public void onItemsSelected();
	
	public void onCreateButtonClicked();
	public void onCancelButtonClicked();
	
	public void onInputFieldUpdated();
}
