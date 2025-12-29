package org.souhub.aihomedesign.client.models

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
public value class SpaceName(public val value: String) {
    public companion object {
        public val Bedroom: SpaceName = SpaceName("space-bedroom")
        public val LivingRoom: SpaceName = SpaceName("space-living-room")
        public val DiningRoom: SpaceName = SpaceName("space-dining-room")
        public val LivingDiningRoom: SpaceName = SpaceName("space-living-dining-room-1")
        public val Studio: SpaceName = SpaceName("space-studio-1")
        public val HomeOffice: SpaceName = SpaceName("space-home-office")
        public val Kitchen: SpaceName = SpaceName("space-kitchen--")
        public val SingleBedroom: SpaceName = SpaceName("space-single-bedroom")
        public val Outdoor: SpaceName = SpaceName("space-outdoor--")
        public val Bathroom: SpaceName = SpaceName("space-bathroom--")
        public val Foyer: SpaceName = SpaceName("space-foyer")
        public val Nursery: SpaceName = SpaceName("space-nursery-1")
        public val KidsRoom: SpaceName = SpaceName("space-kids-room-1")
        public val LivingDiningBedroom: SpaceName = SpaceName("space-living-bed-room-1")
    }
}