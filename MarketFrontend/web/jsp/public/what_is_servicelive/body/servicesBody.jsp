<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="ServiceLive Services"/>
		</jsp:include>
		
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>


				<div id="servicesHero" class="hero clearfix">
					<div class="content">
						<p class="paddingBtm">
							<img title="Find the Right Person for the Job" alt="Find the Right Person for the Job" src="${staticContextPath}/images/what_is_sl/hdr_servicesHero.gif"/>
						</p>
						<p class="paddingBtm">
							On ServiceLive, providers register their skills by category and sub-category, so it's easy for you to find the service professional you need.
						</p>

					</div>
				</div>
				
				<table class="servicesLists" cellpadding="0" cellspacing="0">
					<tr>
						<td class="column1">
							<strong><a href="#" onclick="setHiddenFields('Home Electronics', '1', '', '', '', '1')">Home Electronics &amp; Service &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Plasma &amp; LCD Installation
								</li>
								<li>
									Audio Specialists
								</li>
								<li>
									Home Theater Installation
								</li>
								<li>
									TV Repair
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Computer/Network Services', '100', '', '', '', '1')">Computer/Network Services &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Wireless Networking
								</li>
								<li>
									PC Repair
								</li>
								<li>
									Security/Virus Removal
								</li>
								<li>
									Computer Upgrades
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Appliance Installation', '200', '', '', '', '1')">Appliance Installation &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Stoves &amp; Microwave Ovens
								</li>
								<li>
									Refrigerators
								</li>
								<li>
									Washers &amp; Dryers
								</li>
								<li>
									Dishwashers
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Carpentry and Woodworking', '400', '', '', '', '1')">Carpentry &amp; Woodworking &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Closets &amp; Organization
								</li>
								<li>
									Trim &amp; Moldings
								</li>
								<li>
									Doors
								</li>
								<li>
									Decking
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Plumbing', '800', '816', '', '', '1')">Plumbing &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Faucets/Sinks
								</li>
								<li>
									Water Heater Installation
								</li>
								<li>
									Humidifiers
								</li>
								<li>
									Water Treatment System
								</li>
								<li>
									Sprinkler Systems
								</li>
							</ul>
						</td>
						<td class="column2">
							<strong><a href="#" onclick="setHiddenFields('Heating and Cooling', '600', '', '', '', '1')">Heating &amp; Cooling &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Central Air Conditioning
								</li>
								<li>
									Central Heating
								</li>
								<li>
									Window/Room Air Conditioners
								</li>
								<li>
									General Repair &amp; Maintenance
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Handyman Services', '800', '', '', '', '1')">Handyman Service &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Basic General Services
								</li>
								<li>
									Lights Switches &amp; Outlets
								</li>
								<li>
									Fixtures, Fans, etc.
								</li>
								<li>
									Holiday Lighting
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Roofing and Siding', '1200', '', '', '', '1')">Roofing &amp; Siding &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Roofing
								</li>
								<li>
									Skylights
								</li>
								<li>
									Gutters
								</li>
								<li>
									Siding
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Walls and Ceilings', '800', '822', '', '', '1')">Walls &amp; Ceilings &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Painting Interior/Exterior
								</li>
								<li>
									Sponge or Faux Finishing
								</li>
								<li>
									Textured Finishes
								</li>
								<li>
									Wall Papering
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Flooring', '800', '835', '', '', '1')">Flooring &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Carpeting
								</li>
								<li>
									Vinyl Flooring
								</li>
								<li>
									Tile Flooring
								</li>
								<li>
									Laminate/Wood Flooring
								</li>
							</ul>
						</td>
						<td class="column3">
							<strong><a href="#" onclick="setHiddenFields('Automotive', '1300', '', '', '', '1')">Automotive &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Battery Installation
								</li>
								<li>
									Car Stereo Installation
								</li>
								<li>
									Towing Service
								</li>
								<li>
									Detailing
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Kitchen', '500', '507', '', '', '1')">Kitchen &amp; Countertops &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Counter Top Installation
								</li>
								<li>
									Faucets &amp; Sinks
								</li>
								<li>
									Cabinets
								</li>
								<li>
									Garbage Disposals
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Product Assembly', '300', '', '', '', '1')">Product Assembly &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Patio Furniture
								</li>
								<li>
									Grills &amp; Gazebos
								</li>
								<li>
									Fitness Equipment
								</li>
								<li>
									Lawn Buildings
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Other Services', '1400', '', '', '', '1')">Other Services &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Home Inspection
								</li>
								<li>
									Pest Control
								</li>
								<li>
									Interior Design
								</li>
								<li>
									Land Surveyor
								</li>
							</ul>
							<strong><a href="#" onclick="setHiddenFields('Lawn and Garden', '700', '', '', '', '1')">Lawn &amp; Garden &raquo; </a></strong>
							<ul class="vertBulleted">
								<li>
									Tree &amp; Shrub Service
								</li>
								<li>
									Lawn Care Service
								</li>
								<li>
									Landscaping - Design &amp; Installation
								</li>
								<li>
									Sprinkler Systems
								</li>
							</ul>
						</td>
					</tr>
				</table>
