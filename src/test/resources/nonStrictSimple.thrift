namespace java org.thrift.ton.gen.nonStrictSimple

senum SenumDepricated {
	"senum1",
	"senum2"
}

struct Simple
{
  1:  bool boolField,
  2:  byte byteField,
  3:  i16  i16Field,
  4:  i32  i32Field,
  5:  i64  i64Field,
  6:  double doubleField,
  7:  string stringField,
  8:  binary binaryField,
  9:  SenumDepricated sd
}

service Servica
{
  Simple dajSimple();
}