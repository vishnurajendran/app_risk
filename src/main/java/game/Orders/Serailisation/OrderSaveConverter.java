package game.Orders.Serailisation;
import entity.Player;
import entity.RiskMap;
import game.Orders.*;
import java.util.List;
import java.util.Optional;

/**
 * this class aids in conversion between OrderSaveData and Order
 * @author vishnurajendran
 */
public class OrderSaveConverter {
    public static OrderSaveData createOrderSaveData(Order p_order){
        switch(p_order.getOrderType()){
            case Advance   : return fromAdvanceOrder(p_order);
            case Airlift   : return fromAirliftOrder(p_order);
            case Blockade  : return fromBlockadeOrder(p_order);
            case Bomb      : return fromBombOrder(p_order);
            case Deploy    : return fromDeployOrder(p_order);
            case Negotiate : return fromNegotiateOrder(p_order);
            case Empty     : return fromEmptyOrder(p_order);
            default        : return null;
        }
    }

    /**
     * converts advance order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromAdvanceOrder(Order p_order){
        AdvanceOrder l_advanceOrder = (AdvanceOrder) p_order;
        int l_playerId = l_advanceOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Advance,l_playerId, 0,
                                            l_advanceOrder.getTargetCountry(),
                                            l_advanceOrder.getSourceCountry(),
                                            l_advanceOrder.getArmiesToAdvance());
    }

    /**
     * converts airlift order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromAirliftOrder(Order p_order){
        AirliftOrder l_airliftOrder = (AirliftOrder) p_order;
        int l_playerId = l_airliftOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Airlift,l_playerId, 0,
                l_airliftOrder.getTargetCountry(),
                l_airliftOrder.getSourceCountry(),
                l_airliftOrder.getArmiesToAirlift());
    }

    /**
     * converts blockade order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromBlockadeOrder(Order p_order){
        BlockadeOrder l_blockadeOrder = (BlockadeOrder) p_order;
        int l_playerId = l_blockadeOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Blockade,l_playerId, 0,
                l_blockadeOrder.getTargetCountry(),
                l_blockadeOrder.getSourceCountry(),
                0);
    }

    /**
     * converts bomb order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromBombOrder(Order p_order){
        BombOrder l_bombOrder = (BombOrder) p_order;
        int l_playerId = l_bombOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Bomb,l_playerId, 0,
                l_bombOrder.getTargetCountry(),
                l_bombOrder.getSourceCountry(),
                0);
    }

    /**
     * converts deploy order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromDeployOrder(Order p_order){
        DeployOrder l_deployOrder = (DeployOrder) p_order;
        int l_playerId = l_deployOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Deploy,l_playerId, 0,l_deployOrder.getTargetCountry(),
                l_deployOrder.getSourceCountry(),
                l_deployOrder.getArmiesToDeploy());
    }

    /**
     * converts negotiate order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromNegotiateOrder(Order p_order){
        NegotiateOrder l_negotiateOrder = (NegotiateOrder) p_order;
        int l_playerId = l_negotiateOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Negotiate,l_playerId,
                l_negotiateOrder.getNegotiatedPlayer().getPlayerId(),
                l_negotiateOrder.getTargetCountry(),
                l_negotiateOrder.getSourceCountry(),
                0);
    }

    /**
     * converts empty order to OrderSaveData
     * @param p_order order to convert
     * @return OrderSaveData instance
     */
    private static OrderSaveData fromEmptyOrder(Order p_order){
        EmptyOrder l_negotiateOrder = (EmptyOrder) p_order;
        int l_playerId = l_negotiateOrder.getCtxPlayer().getPlayerId();
        return new OrderSaveData(OrderType.Empty,-1,
                -1,
               -1,
                -1,
                -1);
    }

    public static Order parseOrderSaveData(OrderSaveData p_data, List<Player> p_playerList, RiskMap p_mapReference){
        switch(p_data.getType()){
            case Advance   : return toAdvanceOrder(p_data,p_playerList, p_mapReference);
            case Airlift   : return toAirliftOrder(p_data,p_playerList, p_mapReference);
            case Blockade  : return toBlockadeOrder(p_data,p_playerList, p_mapReference);
            case Bomb      : return toBombOrder(p_data,p_playerList, p_mapReference);
            case Deploy    : return toDeployOrder(p_data,p_playerList, p_mapReference);
            case Negotiate : return toNegotiateOrder(p_data, p_playerList);
            case Empty     : return toEmptyOrder(p_data);
            default        : return null;
        }
    }

    /**
     * returns Player refernce using player id
     * @param id player to search with
     * @return instance of player.
     */
    private static Player getPlayerById(int id, List<Player> p_playerList){
        Optional<Player> l_player = p_playerList.stream().filter((l_ref)->l_ref.getPlayerId()==id).findFirst();
        return l_player.isPresent()?l_player.get() : null;
    }

    /**
     * converts OrderSaveData instance to AdvanceOrder
     * @param p_data save data to convert from
     * @param p_mapReference reference of the map to assign to player
     * @return instance of order.
     */
    private static Order toAdvanceOrder(OrderSaveData p_data, List<Player> p_playerList, RiskMap p_mapReference){
        Player l_player = getPlayerById(p_data.getCtxPlayer(),p_playerList);
        return new AdvanceOrder(l_player, p_data.getSourceCountry(),
                p_data.getTargetCountry(),p_data.getArmyValue(), p_mapReference);
    }

    /**
     * converts OrderSaveData instance to AirliftOrder
     * @param p_data save data to convert from
     * @param p_mapReference reference of the map to assign to player
     * @return instance of order.
     */
    private static Order toAirliftOrder(OrderSaveData p_data, List<Player> p_playerList, RiskMap p_mapReference){
        Player l_player = getPlayerById(p_data.getCtxPlayer(),p_playerList);
        return new AirliftOrder(l_player, p_data.getSourceCountry(),
                p_data.getTargetCountry(),p_data.getArmyValue(), p_mapReference);
    }

    /**
     * converts OrderSaveData instance to BlockadeOrder
     * @param p_data save data to convert from
     * @param p_mapReference reference of the map to assign to player
     * @return instance of order.
     */
    private static Order toBlockadeOrder(OrderSaveData p_data, List<Player> p_playerList, RiskMap p_mapReference){
        Player l_player = getPlayerById(p_data.getCtxPlayer(),p_playerList);
        return new BlockadeOrder(p_mapReference,l_player,
                p_mapReference.getCountryById(p_data.getTargetCountry()));
    }

    /**
     * converts OrderSaveData instance to BombOrder
     * @param p_data save data to convert from
     * @param p_mapReference reference of the map to assign to player
     * @return instance of order.
     */
    private static Order toBombOrder(OrderSaveData p_data, List<Player> p_playerList, RiskMap p_mapReference){
        Player l_player = getPlayerById(p_data.getCtxPlayer(),p_playerList);
        return new BombOrder(l_player, p_data.getTargetCountry(), p_mapReference);
    }

    /**
     * converts OrderSaveData instance to Deploy
     * @param p_data save data to convert from
     * @param p_mapReference reference of the map to assign to player
     * @return instance of order.
     */
    private static Order toDeployOrder(OrderSaveData p_data, List<Player> p_playerList, RiskMap p_mapReference){
        Player l_player = getPlayerById(p_data.getCtxPlayer(),p_playerList);
        return new DeployOrder(l_player, p_data.getArmyValue(),
                p_data.getTargetCountry(), p_mapReference);
    }

    /**
     * converts OrderSaveData instance to NegotiateOrder
     * @param p_data save data to convert from
     * @return instance of order.
     */
    private static Order toNegotiateOrder(OrderSaveData p_data, List<Player> p_playerList){
        Player l_player = getPlayerById(p_data.getCtxPlayer(), p_playerList);
        Player l_otherPlayer = getPlayerById(p_data.getTargetPlayer(), p_playerList);
        return new NegotiateOrder(l_player, l_otherPlayer);
    }

    /**
     * converts OrderSaveData instance to EmptyOrder
     * @param p_data save data to convert from
     * @return instance of order.
     */
    private static Order toEmptyOrder(OrderSaveData p_data){
        return new EmptyOrder();
    }
}
