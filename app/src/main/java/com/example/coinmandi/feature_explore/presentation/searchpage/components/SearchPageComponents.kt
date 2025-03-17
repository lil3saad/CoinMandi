package com.example.coinmandi.feature_explore.presentation.searchpage.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coinmandi.ui.theme.BrandColor

@Composable
fun AllCoinsSearchBar(searchText : String = "",
                      onSearchTextChange : (String) -> Unit,
                      modifier: Modifier = Modifier,
                      performSearch : (String) -> Unit

){
    TextField( value = searchText, onValueChange = { usertext ->
        onSearchTextChange(usertext)
    },
        placeholder = { Text("Search coins by name, symbol") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor =  Color.Transparent,
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = BrandColor
        ),
        trailingIcon = {
            IconButton(onClick = {
                performSearch(searchText)
            } ){
                Icon(imageVector = Icons.Default.Search ,  contentDescription = "Search Icon",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    )
}