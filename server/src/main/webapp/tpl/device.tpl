<td><%= id %></td>
<td><%= deviceName %></td>
<td><%= tvName %></td>
<td class="device-url">
    <span class="show url"><%= deviceUrl ? deviceUrl : "None"  %></span>
    <input type="text" class="form-control url-input hidden">
</td>
<td><%= _.helpers.convertJodaToJs(lastHeartbeat) %></td>
<td>
    <div class="btn-group">
        <button type="button" class="btn btn-info btn-change-url">Change Url</button>
        <button type="button" class="btn btn-success btn-save-url hidden">Save Url</button>
        <button type="button" class="btn btn-danger btn-remove-device">Remove device</button>
    </div>
</td>