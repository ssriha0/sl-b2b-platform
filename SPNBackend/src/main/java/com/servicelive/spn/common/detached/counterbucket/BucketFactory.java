package com.servicelive.spn.common.detached.counterbucket;

public class BucketFactory {

	public static SpnBucket createBucket(Integer spnId) {
		SpnBucket spnBucket = new SpnBucket(spnId);

		GenericBucket invitedComposite = new CompositeBucket(BucketType.INVITED_COMPOSITE);
		GenericBucket interestedComposite = new CompositeBucket(BucketType.INTERESTED_COMPOSITE);
		GenericBucket notInterestedComposite = new CompositeBucket(BucketType.NOT_INTERESTED_COMPOSITE);
		GenericBucket appliedComposite = new CompositeBucket(BucketType.APPLIED_COMPOSITE);
		GenericBucket declinedComposite = new CompositeBucket(BucketType.DECLINED_COMPOSITE);
		GenericBucket applicationIncompleteComposite = new CompositeBucket(BucketType.APPLICATION_INCOMPLETE_COMPOSITE);
		GenericBucket inactiveComposite = new CompositeBucket(BucketType.INACTIVE_COMPOSITE);
		GenericBucket removedComposite = new CompositeBucket(BucketType.REMOVED_COMPOSITE);
		GenericBucket activeComposite = new CompositeBucket(BucketType.ACTIVE_COMPOSITE);
		
		GenericBucket invitedProviderFirm = new InvitedProviderFirm();
		GenericBucket invitedServiceProvider = new InvitedServiceProvider();
		GenericBucket interestedProviderFirm = new InterestedProviderFirm();
		GenericBucket interestedServiceProvider = new InterestedServiceProvider();
		GenericBucket notInterestedProviderFirm = new NotInterestedProviderFirm();
		GenericBucket notInterestedServiceProvider = new NotInterestedServiceProvider();
		GenericBucket appliedProviderFirm = new AppliedProviderFirm();
		GenericBucket appliedServiceProvider = new AppliedServiceProvider();
		GenericBucket declinedProviderFirm = new DeclinedProviderFirm();
		GenericBucket declinedServiceProvider = new DeclinedServiceProvider();
		GenericBucket applicationIncompletedProviderFirm = new ApplicationIncompleteProviderFirm();
		GenericBucket applicationIncompletedProvider = new ApplicationIncompleteServiceProvider();
		GenericBucket activeProviderFirm = new ActiveProviderFirm();
		GenericBucket removedProviderFirm = new RemovedProviderFirm();
		GenericBucket inactiveProviderFirm = new InactiveProviderFirm();


		invitedComposite.addBucket(invitedProviderFirm);
		invitedComposite.addBucket(invitedServiceProvider);
		interestedComposite.addBucket(interestedProviderFirm);
		interestedComposite.addBucket(interestedServiceProvider);
		notInterestedComposite.addBucket(notInterestedProviderFirm);
		notInterestedComposite.addBucket(notInterestedServiceProvider);
		appliedComposite.addBucket(appliedProviderFirm);
		appliedComposite.addBucket(appliedServiceProvider);
		declinedComposite.addBucket(declinedProviderFirm);
		declinedComposite.addBucket(declinedServiceProvider);
		applicationIncompleteComposite.addBucket(applicationIncompletedProviderFirm);
		applicationIncompleteComposite.addBucket(applicationIncompletedProvider);
		inactiveComposite.addBucket(inactiveProviderFirm);
		removedComposite.addBucket(removedProviderFirm);
		activeComposite.addBucket(activeProviderFirm);
		
		
		spnBucket.addBucket(invitedComposite);
		spnBucket.addBucket(interestedComposite);
		spnBucket.addBucket(notInterestedComposite);
		spnBucket.addBucket(appliedComposite);
		spnBucket.addBucket(declinedComposite);
		spnBucket.addBucket(applicationIncompleteComposite);
		spnBucket.addBucket(inactiveComposite);
		spnBucket.addBucket(removedComposite);
		spnBucket.addBucket(activeComposite);

		return spnBucket;
	}

}
