dojo.provide("dojox.layout.LinkPane");

dojo.require("dojox.layout.ContentPane2");
dojo.require("dijit._Templated");

dojo.declare("dojox.layout.LinkPane",
	[dojox.layout.ContentPane2, dijit._Templated],
{
	// summary
	//	LinkPane is just a ContentPane that loads data remotely (via the href attribute),
	//	and has markup similar to an anchor.  The anchor's body (the words between <a> and </a>)
	//	become the title of the widget (used for TabContainer, AccordionContainer, etc.)
	// usage
	//	<a href="foo.html">my title</a>

	// I'm using a template because the user may specify the input as
	// <a href="foo.html">title</a>, in which case we need to get rid of the
	// <a> because we don't want a link.
	templateString: '<div class="dijitLinkPane"></div>',

	postCreate: function(){

		// If user has specified node contents, they become the title
		// (the link must be plain text)
		if(this.srcNodeRef){
			this.title += this.srcNodeRef.innerHTML;
		}

		dojox.layout.LinkPane.superclass.postCreate.apply(this, arguments);

	}
});
