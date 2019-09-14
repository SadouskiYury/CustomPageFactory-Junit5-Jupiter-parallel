package elements;


public interface IElement {

	void waitInvisibilityOfElement();

	void softAssertTextShouldBe(String text);

	Boolean attributeContains(String attribute, String value);

	Boolean attributeEquals(String attribute, String value);

}
