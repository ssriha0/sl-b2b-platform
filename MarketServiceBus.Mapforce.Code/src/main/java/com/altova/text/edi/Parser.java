////////////////////////////////////////////////////////////////////////
//
// Parser.java
//
// This file was generated by MapForce 2011sp1.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text.edi;

import com.altova.text.Generator;
import com.altova.text.ITextNode;

public class Parser {

	Action mErrorSettings[] = {
		Action.Stop,
		Action.Stop,
		Action.Stop,
		Action.Stop,
		Action.ReportReject,
		Action.ReportReject,
		Action.ReportReject,
		Action.ReportReject,
		Action.ReportReject,
		Action.ReportReject,
		Action.ReportReject,
		Action.ReportReject,
		Action.Stop,
		Action.ReportReject,
		Action.ReportReject,
	};

	//counters for edi errors
	char mF717;
	char mF715;
	String msF447 = null;
	long mTransactionSetCount = 0;
	long mTransactionSetAccepted = 0;
	long mCurrentSegmentPos = 0;
	long mComponentDataElementPos = 0;
	long mDataElementPos = 0;

	public enum ErrorType {
		Undefined,
		MissingSegment,
		MissingGroup,
		MissingFieldOrComposite,
		ExtraData,
		FieldValueInvalid,
		InvalidDate,
		InvalidTime,
		ExtraRepeat,
		NumericOverflow,
		DataElementTooShort,
		DataElementTooLong,
		UnexpectedEndOfFile,
		CodelistValueWrong,
		SemanticWrong,
		SegmentUnexpected,
		SegmentUnrecognized,
		ErrorCount
	}
	
	public enum Action {
		Undefined,
		Ignore,
		ReportAccept,
		ReportReject,
		Stop,
	}

	protected EDISettings mSettings;

	//Segment vectors for error reporting
	public final java.util.HashSet<String> StandardSegments = new java.util.HashSet<String>( 1500 );
	public final java.util.HashSet<String> MessageSegments = new java.util.HashSet<String>( 200 );

	public class Context {
		Particle mParticle;
		Parser mParser;
		Scanner mScanner;
		Generator mGenerator;
		Generator mGeneratorForErrors = null;
		Context mParent = null;
		EDISemanticValidator mValidator;
		long mOccurence;

		public Particle getParticle() {
			return mParticle;
		}

		public Parser getParser() {
			return mParser;
		}

		public Scanner getScanner() {
			return mScanner;
		}

		public Generator getGenerator() {
			return mGenerator;
		}

		public Generator getGeneratorForErrors() {
			return mGeneratorForErrors;
		}

		public void createGeneratorForErrors(final String sName)
		{
			mGeneratorForErrors = new Generator();
			mGeneratorForErrors.enterElement( sName, ITextNode.ErrorList );
		}

		public Context getParent() {
			return mParent;
		}

		public Context (Parser parser, Scanner scanner, Particle rootParticle, Generator generator, EDISemanticValidator validator) {
			this.mParticle = rootParticle;
			this.mParser = parser;
			this.mScanner = scanner;
			this.mGenerator = generator;
			this.mValidator = validator;
			this.mOccurence = 0;
		}

		public Context (Context parent, Particle newParticle) {
			this.mParticle = newParticle;
			this.mParser = parent.mParser;
			this.mScanner = parent.mScanner;
			this.mGenerator = parent.mGenerator;
			this.mValidator = parent.mValidator;
			this.mParent = parent;
			this.mOccurence = 0;

			if( newParticle.mNameOverride.equals( "Message" ) )
			{
				mGeneratorForErrors = new Generator();
				mGeneratorForErrors.enterElement( "ParserErrors_Message", ITextNode.ErrorList );
			}
			else
			if( newParticle.mNameOverride.equals( "Group" ) )
			{
				mGeneratorForErrors = new Generator();
				mGeneratorForErrors.enterElement( "ParserErrors_Group", ITextNode.ErrorList );
			}
		}

		public Parser.Context newContext(Parser.Context context, Particle particle) {
			return new Parser.Context(context, particle);
		}

		public void handleError( ErrorType error, String message, ErrorPosition position )
		{
			handleError( error, message, position, "" );
		}

		public void handleError( ErrorType error, String message, ErrorPosition position, String originalData )
		{
			Generator gen = findGenerator();
			String location = mParticle.getNode().getName();
			Context parent = mParent;
			while (parent != null)
			{
				location = parent.getParticle().getNode().getName() + " / " + location;
				parent = parent.getParent();
			}

			String lineLoc = String.format( "Line %d column %d (offset 0x%x): ", position.getLine(), position.getColumn() + 1, position.getPosition() );
			location = lineLoc + location;
			
			switch ( mErrorSettings[error.ordinal()] )
			{
			case Stop:
			{
				throw new com.altova.AltovaException (location + ": " + message);
			}
			case ReportReject:
			{
				mParser.mF717 = 'R';
				mParser.mF715 = 'R';
				System.out.println("Warning: " + location + ": " + message);
			}
			break;
			case ReportAccept:
			{
				//only change Transaction Set Code from Accepted state here.
				if( mParser.mF717 == 'A')
				{
					mParser.mF717 = 'E';
					mParser.mF715 = 'E';
				}
				System.out.println("Warning: " + location + ": " + message);
			}
			break;
			case Ignore:
				gen = null;
				break;
			}

			if( gen != null )
			{
				
				{
					byte nodeClass = mParticle.getNode().getNodeClass();
					String currentSegment = getCurrentSegmentName();

					if( error == ErrorType.MissingGroup ||
						error == ErrorType.ExtraRepeat ||
						nodeClass == ITextNode.Segment ||
						nodeClass == ITextNode.Composite ||
						nodeClass == ITextNode.DataElement )
					{
						gen.enterElement( "LoopMF_AK3", ITextNode.Group );
						gen.enterElement( "MF_AK3", ITextNode.Group );

						String segmentSyntaxErrorCode = "";
						switch( error)
						{
						case ExtraRepeat:
						{
							if (nodeClass == ITextNode.Group)
								segmentSyntaxErrorCode = "4";
							else
								segmentSyntaxErrorCode = "5";
						}
						break;
						case MissingGroup:
						case MissingSegment: segmentSyntaxErrorCode = "3"; break;
						case SegmentUnexpected:
						{
							segmentSyntaxErrorCode = "2";
							currentSegment = originalData;
						}
						break;
						case SegmentUnrecognized:
						{
							segmentSyntaxErrorCode = "1";
							currentSegment = originalData;
						}
						break;
						default:
							if( nodeClass == ITextNode.Composite ||
								nodeClass == ITextNode.DataElement )
								segmentSyntaxErrorCode = "8";
							else
								segmentSyntaxErrorCode = "2";
						}

						gen.insertElement( "F721", currentSegment, ITextNode.DataElement );
						gen.insertElement( "F719", "" + mParser.getCurrentSegmentPos(), ITextNode.DataElement );
						if( mParser.getF447() != null && mParser.getF447().length() > 0)
							gen.insertElement( "F447", mParser.getF447(), ITextNode.DataElement );

						gen.insertElement( "F720", segmentSyntaxErrorCode, ITextNode.DataElement );
						gen.insertElement( "ErrorMessage", message, ITextNode.DataElement );
						gen.leaveElement( "MF_AK3" );
					}

					if( nodeClass == ITextNode.Composite ||
						nodeClass == ITextNode.DataElement )
					{
						// error in data element
						gen.enterElement( "MF_AK4", ITextNode.Group );
						gen.enterElement( "C030", ITextNode.Composite );
						gen.insertElement( "F722", "" + mParser.getDataElementPos(), ITextNode.DataElement );

						// is optional F1528
						if( mParser.mComponentDataElementPos > 0)
							gen.insertElement( "F1528", "" + mParser.getComponentDataElementPos(), ITextNode.DataElement);

						// is optional F1686
						if( mOccurence > 0)
							gen.insertElement( "F1686", "" + mOccurence, ITextNode.DataElement);

						gen.leaveElement( "C030" );

						String dataElementReferenceNumber = mParticle.getNode().getName().substring( 1 );	// F1234 -> 1234
						gen.insertElement( "F725", dataElementReferenceNumber, ITextNode.DataElement );

						String dataElementSyntaxErrorCode = "" + getX12DataElementErrorCode( error );
						gen.insertElement( "F723", dataElementSyntaxErrorCode, ITextNode.DataElement );
						gen.insertElement( "F724", originalData, ITextNode.DataElement );
						gen.insertElement( "ErrorMessage", message, ITextNode.DataElement );
						gen.leaveElement( "MF_AK4" );
					}

					if( error == ErrorType.MissingGroup ||
						error == ErrorType.ExtraRepeat ||
						nodeClass == ITextNode.Segment ||
						nodeClass == ITextNode.Composite ||
						nodeClass == ITextNode.DataElement )
					{
						gen.leaveElement( "LoopMF_AK3" );
					}
				}
			}
		}

		Generator findGenerator()
		{
			if( mGeneratorForErrors != null )
				return mGeneratorForErrors;
			if( mParent != null )
				return mParent.findGenerator();
			return null;
		}

		String getCurrentSegmentName()
		{
			if( mParticle.getNode().getNodeClass() == ITextNode.Group &&
				mParticle.getNode().getChildCount() > 0)
				return mParticle.getNode().child(0).getName();
			if( mParticle.getNode().getNodeClass() == ITextNode.Segment )
				return mParticle.getNode().getName();
			if( mParent != null )
				return mParent.getCurrentSegmentName();
			return "";
		}

		int getX12DataElementErrorCode( ErrorType error )
		{
			switch( error )
			{
				case MissingFieldOrComposite : return 1;
				case ExtraData : return 3;
				case ExtraRepeat : return 3;
				case DataElementTooShort : return 4;
				case DataElementTooLong : return 5;
				case FieldValueInvalid : return 6;
				case CodelistValueWrong : return 7;
				case InvalidDate: return 8;
				case InvalidTime: return 9;
				case SemanticWrong: return 10;
				default : return 1;
			}
		}

		public EDISemanticValidator getValidator()
		{
			return mValidator;
		}


		public void setOccurence( long nOccurence)
		{
			mOccurence = nOccurence;
		}
	}

	boolean mIgnoreMaxOccurs = true;

	public boolean getIgnoreMaxOccurs()  {
		return mIgnoreMaxOccurs;
	}

	public void setIgnoreMaxOccurs(boolean imo) {
		mIgnoreMaxOccurs = imo;
	}

	public Action[] getErrorSettings() {
		return mErrorSettings;
	}

	public void setErrorSettings( Action[] settings) {
		mErrorSettings = settings;
	}

    public int getEDIKind() {
    	return mSettings.getStandard();
    }

    public EDISettings getSettings() {
    	return mSettings;
    }

	public long getDataElementPos() {
		return mDataElementPos;
	}

	public void resetDataElementPos() {
		mDataElementPos = 0;
	}

	public void incrementDataElementPos() {
		mDataElementPos++;
	}

	public long getComponentDataElementPos() {
		return mComponentDataElementPos;
	}

	public void resetComponentDataElementPos() {
		mComponentDataElementPos = 0;
	}

	public void incrementComponentDataElementPos() {
		mComponentDataElementPos++;
	}

	public long getCurrentSegmentPos() {
		return mCurrentSegmentPos;
	}

	public void incrementCurrentSegmentPos() {
		mCurrentSegmentPos++;
	}

	public void resetCurrentSegmentPos() {
		mCurrentSegmentPos = 1;
	}

	public long getTransactionSetCount() {
		return mTransactionSetCount;
	}

	public void incrementTransactionSetCount() {
		mTransactionSetCount++;
	}

	public void resetTransactionSetCount() {
		mTransactionSetCount = 0;
	}

	public long getTransactionSetAccepted() {
		return mTransactionSetAccepted;
	}

	public void incrementTransactionSetAccepted() {
		mTransactionSetAccepted++;
	}

	public void resetTransactionSetAccepted() {
		mTransactionSetAccepted = 0;
	}

	public char getF715() {
		return mF715;
	}

	public void setF715( char chr) {
		mF715 = chr;
	}

	public char getF717() {
		return mF717;
	}

	public void setF717( char chr) {
		mF717 = chr;
	}

	public String getF447() {
		return msF447;
	}

	public void setF447( String sF447) {
		msF447 = sF447;
	}

	public boolean parse (Particle rootParticle, String buffer, Generator generator, EDISettings settings) {
		ServiceChars sc = settings.getServiceChars();
		Scanner scanner = new Scanner(buffer, sc);
		EDISemanticValidator validator = new EDISemanticValidator( settings);

		//init transaction counter for X12
		mF715 = 'A';
		// the first ST segment for X12 will set this to 'A' (Accepted)
		// if there is no 'ST' segment there must be something wrong
		mF717 = 'R';
		resetTransactionSetCount();
		resetTransactionSetAccepted();

		Context rootContext = new Context (this, scanner, rootParticle, generator, validator);
		boolean bOk = rootParticle.getNode().read(rootContext);
		scanner.skipWhitespace();
		if ( !bOk || !scanner.isAtEnd() )
		{
			Scanner.State beforeReadState = scanner.getCurrentState();
			String sExtra= scanner.consumeString( ServiceChars.SegmentTerminator, true).toString();
			rootContext.handleError( 
				ErrorType.Undefined, 
				ErrorMessages.GetTextNotParsedMessage( sExtra),
				new ErrorPosition( beforeReadState )
			);
		}
				
		return bOk;
	}
}