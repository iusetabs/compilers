/* Generated By:JavaCC: Do not edit this line. MyParser.java */
        public class MyParser implements MyParserConstants {
                public static void main (String [] args){
                        MyParser tokeniser;
                        //write shit here
                        if ( args.length == 0){
                                System.out.println("Reading from standard input....");
                                tokeniser = new MyParser(System.in);
                        }else if (args.length == 1){
                                try{
                                        tokeniser = new MyParser(new java.io.FileInputStream(args[0]));
                                } catch (java.io.FileNotFoundException e) {
                                        System.err.println("File " + args[0] + " not found.");
                                        return;
                                }
                        }
                        else{

                                System.out.println("SLP Tokeniser: Usage is one of:");
                                System.out.println("java SLPTokeniser < inputfile");
                                System.out.println("OR");
                                System.out.println("java SLPTokeniser inputfile");
                                return;
                        }
                        /*
			* We’ve now initialised the tokeniser to read from the appropriate place,
			* so just keep reading tokens and printing them until we hit EOF
			*/
                        try {
                                tokeniser.Prog();
                        }catch(ParseException e) {
                                System.out.println(e.getMessage());
                                System.out.println("SLP Interpreter: Encountered errors during parse");
                        }
                        for (Token t = getNextToken(); t.kind!=EOF; t = getNextToken()) {
                                // Print out the actual text for the constants, identifiers etc.
                                if (t.kind==INTEGER)
                                {
                                        System.out.print("Integer");
                                        System.out.print("( Kind: " + t.kind + " Image: " + t.image + " ");
                                        System.out.println("Token: " + token + ")");
                                }
                                else if (t.kind==ID)
                                {
                                        System.out.print("Identifier");
                                        System.out.print("( Kind: " + t.kind + " Image: " + t.image + " ");
                                        System.out.println("Token: " + token + ")");
                                }
                                else{
                                        System.out.print("( Kind: " + t.kind + " Image: " + t.image + " ");
                                        System.out.println("Token: " + token + ")");
                                }
                        }
                }

/* 
 ___________________________
|                           |
|	SECTION FOUR        |
|       GRAMMAR RULES       |
|___________________________|
*/
  static final public void Prog() throws ParseException {
    decl_list();
    func_list();
    main();
    jj_consume_token(0);
  }

  static final public void decl_list() throws ParseException {
    decl();
    jj_consume_token(SEMICOLUMN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DECL_VARIABLE:
    case DECL_CONSTANT:
      decl_list();
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
  }

  static final public void decl() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DECL_VARIABLE:
      var_decl();
      break;
    case DECL_CONSTANT:
      const_decl();
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void var_decl() throws ParseException {
    jj_consume_token(DECL_VARIABLE);
    jj_consume_token(ID);
    jj_consume_token(COLUMN);
    type();
  }

  static final public void const_decl() throws ParseException {
    jj_consume_token(DECL_CONSTANT);
    jj_consume_token(ID);
    jj_consume_token(COLUMN);
    type();
    jj_consume_token(IS_VALUE);
    expression();
  }

  static final public void type() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DECL_INTEGER:
      jj_consume_token(DECL_INTEGER);
      break;
    case BOOLEAN:
      jj_consume_token(BOOLEAN);
      break;
    case VOID:
      jj_consume_token(VOID);
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void func_list() throws ParseException {
    function();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DECL_INTEGER:
    case VOID:
    case BOOLEAN:
      func_list();
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
  }

  static final public void function() throws ParseException {
    type();
    jj_consume_token(ID);
    parameter_list();
    jj_consume_token(IS);
    decl_list();
    jj_consume_token(BEGIN);
    statement_block();
    jj_consume_token(RETURN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IS_TRUE:
    case IS_FALSE:
    case OPEN_BRACKET:
    case MINUS_SIGN:
    case INTEGER:
    case ID:
      expression();
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(END);
  }

  static final public void parameter_list() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      nemp_parameter_list();
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
  }

  static final public void nemp_parameter_list() throws ParseException {
    jj_consume_token(ID);
    jj_consume_token(COLUMN);
    type();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COMMA:
      jj_consume_token(COMMA);
      nemp_parameter_list();
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
  }

  static final public void main() throws ParseException {
    jj_consume_token(MAIN);
    jj_consume_token(BEGIN);
    decl_list();
    statement_block();
    jj_consume_token(END);
  }

  static final public void statement_block() throws ParseException {
    statement();
    statement_block();
  }

  static final public void statement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IS_VALUE:
        jj_consume_token(IS_VALUE);
        expression();
        break;
      case OPEN_BRACKET:
        jj_consume_token(OPEN_BRACKET);
        arg_list();
        jj_consume_token(CLOSE_BRACKET);
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      jj_consume_token(SEMICOLUMN);
      break;
    case BEGIN:
      jj_consume_token(BEGIN);
      statement_block();
      jj_consume_token(END);
      break;
    case IF:
      jj_consume_token(IF);
      condition();
      jj_consume_token(BEGIN);
      statement_block();
      jj_consume_token(END);
      break;
    case ELSE:
      jj_consume_token(ELSE);
      jj_consume_token(BEGIN);
      statement_block();
      jj_consume_token(END);
      break;
    case WHILE:
      jj_consume_token(WHILE);
      condition();
      jj_consume_token(BEGIN);
      statement_block();
      jj_consume_token(END);
      break;
    case DO_SKIP:
      jj_consume_token(DO_SKIP);
      jj_consume_token(SEMICOLUMN);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void expression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IS_TRUE:
    case IS_FALSE:
    case MINUS_SIGN:
    case INTEGER:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS_SIGN:
        jj_consume_token(MINUS_SIGN);
        jj_consume_token(ID);
        break;
      case INTEGER:
        jj_consume_token(INTEGER);
        break;
      case IS_TRUE:
        jj_consume_token(IS_TRUE);
        break;
      case IS_FALSE:
        jj_consume_token(IS_FALSE);
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      label_1:
      while (true) {
        binary_arith_op();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MINUS_SIGN:
        case ID:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case MINUS_SIGN:
            jj_consume_token(MINUS_SIGN);
            break;
          default:
            jj_la1[10] = jj_gen;
            ;
          }
          jj_consume_token(ID);
          break;
        case INTEGER:
          jj_consume_token(INTEGER);
          break;
        case IS_TRUE:
          jj_consume_token(IS_TRUE);
          break;
        case IS_FALSE:
          jj_consume_token(IS_FALSE);
          break;
        default:
          jj_la1[11] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS_SIGN:
        case MINUS_SIGN:
          ;
          break;
        default:
          jj_la1[12] = jj_gen;
          break label_1;
        }
      }
      break;
    case ID:
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPEN_BRACKET:
        jj_consume_token(OPEN_BRACKET);
        arg_list();
        jj_consume_token(CLOSE_BRACKET);
        break;
      case IS_TRUE:
      case IS_FALSE:
      case INTEGER:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INTEGER:
          jj_consume_token(INTEGER);
          break;
        case IS_TRUE:
          jj_consume_token(IS_TRUE);
          break;
        case IS_FALSE:
          jj_consume_token(IS_FALSE);
          break;
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        label_2:
        while (true) {
          binary_arith_op();
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case MINUS_SIGN:
          case ID:
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MINUS_SIGN:
              jj_consume_token(MINUS_SIGN);
              break;
            default:
              jj_la1[14] = jj_gen;
              ;
            }
            jj_consume_token(ID);
            break;
          case INTEGER:
            jj_consume_token(INTEGER);
            break;
          case IS_TRUE:
            jj_consume_token(IS_TRUE);
            break;
          case IS_FALSE:
            jj_consume_token(IS_FALSE);
            break;
          default:
            jj_la1[15] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case PLUS_SIGN:
          case MINUS_SIGN:
            ;
            break;
          default:
            jj_la1[16] = jj_gen;
            break label_2;
          }
        }
        break;
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    case OPEN_BRACKET:
      jj_consume_token(OPEN_BRACKET);
      expression();
      jj_consume_token(CLOSE_BRACKET);
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void binary_arith_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS_SIGN:
      jj_consume_token(PLUS_SIGN);
      break;
    case MINUS_SIGN:
      jj_consume_token(MINUS_SIGN);
      break;
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void condition() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OPEN_BRACKET:
      jj_consume_token(OPEN_BRACKET);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TILDA:
        jj_consume_token(TILDA);
        break;
      default:
        jj_la1[20] = jj_gen;
        ;
      }
      expression();
      comp_op();
      expression();
      jj_consume_token(CLOSE_BRACKET);
      break;
    case IS_TRUE:
    case IS_FALSE:
    case MINUS_SIGN:
    case TILDA:
    case INTEGER:
    case ID:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TILDA:
        jj_consume_token(TILDA);
        break;
      default:
        jj_la1[21] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPEN_BRACKET:
        jj_consume_token(OPEN_BRACKET);
        expression();
        jj_consume_token(CLOSE_BRACKET);
        break;
      case IS_TRUE:
      case IS_FALSE:
      case MINUS_SIGN:
      case INTEGER:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case MINUS_SIGN:
          jj_consume_token(MINUS_SIGN);
          jj_consume_token(ID);
          break;
        case INTEGER:
          jj_consume_token(INTEGER);
          break;
        case IS_TRUE:
          jj_consume_token(IS_TRUE);
          break;
        case IS_FALSE:
          jj_consume_token(IS_FALSE);
          break;
        default:
          jj_la1[22] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        label_3:
        while (true) {
          binary_arith_op();
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case MINUS_SIGN:
          case ID:
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MINUS_SIGN:
              jj_consume_token(MINUS_SIGN);
              break;
            default:
              jj_la1[23] = jj_gen;
              ;
            }
            jj_consume_token(ID);
            break;
          case INTEGER:
            jj_consume_token(INTEGER);
            break;
          case IS_TRUE:
            jj_consume_token(IS_TRUE);
            break;
          case IS_FALSE:
            jj_consume_token(IS_FALSE);
            break;
          default:
            jj_la1[24] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case PLUS_SIGN:
          case MINUS_SIGN:
            ;
            break;
          default:
            jj_la1[25] = jj_gen;
            break label_3;
          }
        }
        break;
      case ID:
        jj_consume_token(ID);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OPEN_BRACKET:
          jj_consume_token(OPEN_BRACKET);
          arg_list();
          jj_consume_token(CLOSE_BRACKET);
          break;
        case IS_TRUE:
        case IS_FALSE:
        case INTEGER:
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case INTEGER:
            jj_consume_token(INTEGER);
            break;
          case IS_TRUE:
            jj_consume_token(IS_TRUE);
            break;
          case IS_FALSE:
            jj_consume_token(IS_FALSE);
            break;
          default:
            jj_la1[26] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          label_4:
          while (true) {
            binary_arith_op();
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MINUS_SIGN:
            case ID:
              switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
              case MINUS_SIGN:
                jj_consume_token(MINUS_SIGN);
                break;
              default:
                jj_la1[27] = jj_gen;
                ;
              }
              jj_consume_token(ID);
              break;
            case INTEGER:
              jj_consume_token(INTEGER);
              break;
            case IS_TRUE:
              jj_consume_token(IS_TRUE);
              break;
            case IS_FALSE:
              jj_consume_token(IS_FALSE);
              break;
            default:
              jj_la1[28] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case PLUS_SIGN:
            case MINUS_SIGN:
              ;
              break;
            default:
              jj_la1[29] = jj_gen;
              break label_4;
            }
          }
          break;
        default:
          jj_la1[30] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[31] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      comp_op();
      expression();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR_SIGN:
        jj_consume_token(OR_SIGN);
        break;
      case AND_SIGN:
        jj_consume_token(AND_SIGN);
        break;
      default:
        jj_la1[32] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      condition();
      break;
    default:
      jj_la1[33] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void comp_op() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EQUALS_SIGN:
      jj_consume_token(EQUALS_SIGN);
      break;
    case NEGATION_EQUALS:
      jj_consume_token(NEGATION_EQUALS);
      break;
    case LESS_THAN:
      jj_consume_token(LESS_THAN);
      break;
    case EQUAL_LESS_THAN:
      jj_consume_token(EQUAL_LESS_THAN);
      break;
    case GREATER_THAN:
      jj_consume_token(GREATER_THAN);
      break;
    case EQUAL_GREATER_THAN:
      jj_consume_token(EQUAL_GREATER_THAN);
      break;
    default:
      jj_la1[34] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void arg_list() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      nemp_arg_list();
      break;
    default:
      jj_la1[35] = jj_gen;
      ;
    }
  }

  static final public void nemp_arg_list() throws ParseException {
    jj_consume_token(ID);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[36] = jj_gen;
        break label_5;
      }
      jj_consume_token(COMMA);
      jj_consume_token(ID);
    }
  }

  static final public int BinOp() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS_SIGN:
      jj_consume_token(PLUS_SIGN);
                      {if (true) return 1;}
      break;
    case MINUS_SIGN:
      jj_consume_token(MINUS_SIGN);
                         {if (true) return 2;}
      break;
    default:
      jj_la1[37] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public MyParserTokenManager token_source;
  static JavaCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[38];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0xa000,0xa000,0x214000,0x214000,0x80180000,0x0,0x8000000,0xc0000000,0x41c80,0x180000,0x0,0x180000,0x0,0x180000,0x0,0x180000,0x0,0x80180000,0x80180000,0x0,0x0,0x0,0x180000,0x0,0x180000,0x0,0x180000,0x0,0x180000,0x0,0x80180000,0x80180000,0x0,0x80180000,0x0,0x0,0x8000000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x9004,0x8000,0x0,0x0,0x8000,0x1004,0x4,0x9004,0x6,0x1000,0x4,0x9004,0x6,0x1000,0x9004,0x6,0x8,0x8,0x1004,0x4,0x9004,0x6,0x1000,0x4,0x9004,0x6,0x1000,0x9004,0x30,0x900c,0xfc0,0x8000,0x0,0x6,};
   }

  /** Constructor with InputStream. */
  public MyParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MyParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MyParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public MyParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new MyParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public MyParser(MyParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(MyParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 38; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[49];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 38; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 49; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

        }
