            <h2 class="margin-top">Completion Details</h2>
            <div class="row">
              <div class="col-sm-3 col-md-5">
                <h3>Completion Date</h3>
              </div>
              <div class="col-sm-9 col-md-7">
                <p>${lmTabDTO.lead.completionDate},&nbsp;${lmTabDTO.lead.completionTime }</p>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 col-md-5">
                <h3>Provider</h3>
              </div>
              <div class="col-sm-9 col-md-7">
                <p>${lmTabDTO.lead.resFirstName}&nbsp;${lmTabDTO.lead.resLastName}</p>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 col-md-5">
                <h3>Number of Trips</h3>
              </div>
              <div class="col-sm-9 col-md-7">
                <p>${lmTabDTO.lead.numberOfTrips}&nbsp;on-site visit</p>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 col-md-5">
                <h3>Fee Collected</h3>
              </div>
              <div class="col-sm-9 col-md-7">
                <!-- If values for both Parts and Labor exist, display the breakdown in a tooltip. -->
                <p><span class="tooltip-target" data-toggle="tooltip" data-placement="top" data-original-title="Parts: ${lmTabDTO.lead.leadMaterialPrice}, Labor: ${lmTabDTO.lead.leadLaborPrice}"> ${lmTabDTO.lead.leadFinalPrice}</span></p>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 col-md-5">
                <h3>Comments</h3>
              </div>
              <div class="col-sm-9 col-md-7" style="word-wrap:break-word;">
                <p>${lmTabDTO.lead.completionComments}</p>
              </div>
            </div>