/**
 * Chapter 3: Enums
 */

// Enums in Scala 2 emulated with sealed traits/abstract classes
// and a combination of case objects with case classes.
sealed trait ShipmentStatus
case object InPreparation extends ShipmentStatus
case object Dispatched extends ShipmentStatus
case object InTransit extends ShipmentStatus
case object Delivered extends ShipmentStatus

/**
 * Example 1: Simple enums
 * [] usage, compiler errors, usage (exhaustive pattern matching, values, valueOf, ordinal, fromOrdinal)
 * [] multiline enums
 * [] enum methods/companion object
 */

/**
 * Example 2: ADTs
 * [] syntax sugar
 * [] type widening
 * [] type params
 */
sealed abstract class Customer(val priority: Int)
object Customer {
  case class Standard(name: String) extends Customer(priority = 3)
  case class Premium(name: String) extends Customer(priority = 1)
  case class Business(companyName: String, vatId: String) extends Customer(priority = 2)
}

/**
 * Exercises
 */
object enumsExercises:
  //
  // Exercise 1: Convert to Scala 3 enum syntax.
  //
  enum OrderStatus:
    case Initiated
    case Cancelled
    case Confirmed
    case Fulfilled
    case Refunded
    case Failed


    final def isLegalSuccessorOf(status: OrderStatus): Boolean =
      OrderStatus.legalSuccessors.getOrElse(status, Set.empty).contains(this)

  object OrderStatus:
    private val legalSuccessors: Map[OrderStatus, Set[OrderStatus]] = Map(
      Initiated -> Set(Confirmed, Cancelled),
      Confirmed -> Set(Fulfilled, Failed),
      Fulfilled -> Set(Refunded, Failed)
    )

  //
  // Exercise 2: Implement the following ADT using the new Scala 3 enum sytanx.
  //
  enum PaymentAuthorizationError(retriable: Boolean):
    case IllegalPaymentStatus(existingPaymentId: PaymentId, existingPaymentStatus: PaymentStatus) extends PaymentAuthorizationError(retriable = false)
    case IllegalRequestData(reason: String) extends PaymentAuthorizationError(retriable = false)
    case CustomerUnknown(unknownCustomerId: CustomerId) extends PaymentAuthorizationError(retriable = false)
    case InvalidToken(invalidToken: Token) extends PaymentAuthorizationError(retriable = true)

  // Some type aliases to make the exercise compile...
  // We'll later see how to replace type aliases the new kind of types in Scala 3
  // called *opaque types* for better type safety.
  type PaymentId = String
  type PaymentStatus = String
  type CustomerId = String
  type Token = String
